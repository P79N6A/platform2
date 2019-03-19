package com.springboot.service.route.impl;

import com.springboot.config.redis.RedisConfig;
import com.springboot.model.bean.Channel;
import com.springboot.model.route.RouteMsg;
import com.springboot.model.route.RouteRule;
import com.springboot.model.route.SourceMsg;
import com.springboot.service.pubsub.Publisher;
import com.springboot.service.route.RouteMsgService;
import com.springboot.service.route.RouteService;

import com.springboot.util.ChannelUtil;
import com.springboot.util.JaXmlBeanUtil;
import com.springboot.util.socket.HengShuiTCPClientService;
import com.springboot.util.socket.XinWangTCPClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Service
public class RouteServiceImpl implements RouteService {

    /**
     * 平台发给新网的消息队列名称
     */
    @Value("${XW_CHANNEL_NAME}")
    String XW_CHANNEL_NAME;
    /**
     * 平台发给衡水银行的消息队列名称
     */
    @Value("${HS_CHANNEL_NAME}")
    String HS_CHANNEL_NAME;

    /**
     * 处理完的放入已处理队列：由平台回调渠道
     */
    @Value("${PLATFORM_CHANNEL_NAME_Processed}")
    String PLATFORM_CHANNEL_NAME_Processed;

    @Autowired
    RedisConfig redisConfig;
    @Autowired
    HengShuiTCPClientService hengShuiTcpClientService;
    @Autowired
    XinWangTCPClientService xwTcpClientService;
    @Autowired
    RouteMsgService routeMsgService;

    @Value("${channelCallbackUrl}")
    private String channelCallbackUrl;
    @Value("${platformCallbackUrl}")
    private String platformCallbackUrl;

    @Override
    public String process(String reqxml) {
        Logger logger= LoggerFactory.getLogger(this.getClass());
        String result = "";
        SourceMsg sourceMsg = (SourceMsg) JaXmlBeanUtil.parseXmlToBean(SourceMsg.class,reqxml);

        String channel = ChannelUtil.getChannelName(sourceMsg.getChannelCode());
        //转发
        String destBank=getOneRouteRulePath(channel,"hand");
        String studentxml = sourceMsg.getMsg();
        RouteMsg routeMsg=new RouteMsg();
        routeMsg.setChannelTime(sourceMsg.getChannelTime());
        routeMsg.setChannelDate(sourceMsg.getChannelDate());
        routeMsg.setChannelSeq(sourceMsg.getChannelSeq());
        routeMsg.setChannelCode(sourceMsg.getChannelCode());
        routeMsg.setDestBankCode(destBank);;
        routeMsg.setPlatformCode("xujin001");
        routeMsg.setMsg(studentxml);


        routeMsg.setMaterial(sourceMsg.getMaterial());

        routeMsg.setCallbackUrl(platformCallbackUrl);
        String nextReqxml=JaXmlBeanUtil.parseBeanToXml(RouteMsg.class,routeMsg);

        if (destBank.contains("XWBank")) {

            //放入新网消息队列

           //publish(nextReqxml,XW_CHANNEL_NAME,"toXWProcessQueue");
            try {
                result=xwTcpClientService.transportOut(nextReqxml);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }

        }
        if(destBank.contains("HengShuiBank")) {
            //放入衡水银行消息队列，异步
            //publish(nextReqxml,HS_CHANNEL_NAME,"toHSProcessQueue");
            try {
                result=hengShuiTcpClientService.transportOut(nextReqxml);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        routeMsgService.addRouteMsg(nextReqxml);
        int i=routeMsgService.addRouteMsgBean(routeMsg);
        if(i==1){
            logger.info("platform add routemsg success");
        }else{
            logger.info("platform add routemsg fail!");
        }
        return result;
    }


    public String publish(String message,String channel_name,String type) {
        JedisPool JEDIS_POOL = redisConfig.redisPoolFactory();
        final Jedis publisherJedis = JEDIS_POOL.getResource();
        //主线程：发布消息到CHANNEL_NAME频道上
        new Publisher(publisherJedis, channel_name).startPublish(message);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread();
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("platform publish 发布："+type);
                //发布消息
                publisherJedis.publish(channel_name, message);
            }
        }).start();
        publisherJedis.close();
        return "platform publish to "+type+"ok";
    }
    @Override
    public String getOneRouteRulePath(String sourceChannelCode,String hand){
        RouteRule routeRule=new RouteRule();
        //routeRule.initRouteByHand();
       String destBank= routeRule.getRouteByHand(sourceChannelCode,hand);
        return destBank;
    }

}
