package com.springboot.controller;


import com.springboot.config.redis.RedisConfig;
import com.springboot.model.bean.Student;
import com.springboot.model.route.*;
import com.springboot.service.pubsub.MyListener;
import com.springboot.service.pubsub.Publisher;
import com.springboot.service.pubsub.SubThread;
import com.springboot.service.pubsub.Subscriber;
import com.springboot.service.route.RouteMsgService;
import com.springboot.service.route.RouteRuleService;
import com.springboot.service.route.RouteService;
import com.springboot.util.FileUtil;
import com.springboot.util.GsonUtil;
import com.springboot.util.JaXmlBeanUtil;
import com.springboot.util.RestClient;
import jodd.util.Base64;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


@RestController
@RequestMapping("/redis/api")
public class RedisController {


    @Autowired
    private RouteMsgService routeMsgService;
    @Autowired
    private RouteRuleService routeRuleService;
    @Value("${channelCallbackUrl}")
    private String channelCallbackUrl;
    //本机测试消息队列处理

    /**
     * 放入平台正在处理中队列
     */
    @Value("${PLATFORM_CHANNEL_NAME}")
    String PLATFORM_CHANNEL_NAME;
    /**
     *  放入平台正在处理中队列
     */
    @Value("${PLATFORM_CHANNEL_NAME_Processing}")
    String PLATFORM_CHANNEL_NAME_Processing;
    /**
     * 处理完的放入已处理队列：由平台回调渠道
     */
    @Value("${PLATFORM_CHANNEL_NAME_Processed}")
    String PLATFORM_CHANNEL_NAME_Processed;

    private final static Logger logger = LoggerFactory.getLogger(RedisController.class);


    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    RedisConfig redisConfig;
    @Autowired
    MyListener listener;
    @Autowired
    CountDownLatch latch;
    @Autowired
    StringRedisTemplate template;
    @Value("${initRedis.configFile}")
    private String configFile;
    @Value("${initRedis.needed}")
    private boolean needInitRedis;

    /**
     * refresh redis,when modified
     * @return
     */
    @RequestMapping(value="/refresh",method=RequestMethod.POST)
    @ResponseBody
    public String refreshRedis(){
        FileUtil.toArrayByInputStreamReader(configFile);
        return "refresh success";
    }
    @RequestMapping(value="/init",method=RequestMethod.POST)
    @ResponseBody
    public void initRedis(){
        if(needInitRedis==true){
            logger.info("init Redis hot route");
            FileUtil.toArrayByInputStreamReader(configFile);
        }
    }

    @RequestMapping(value="/receiveFromRisk",method=RequestMethod.POST)
    @ResponseBody
    public void receiveFromRisk(@RequestBody String params ){
        logger.info("in platform receiveFromRisk method!");
        logger.info("receive from bank callback:"+params);
        logger.info("response from Bank"+params);
        logger.info(RestClient.restTemplate(channelCallbackUrl,params));

    }
    @RequestMapping(value="/callbackFromBank",method=RequestMethod.POST)
    @ResponseBody
    public void receiveFromBank(@RequestBody String params ){
        System.out.println("in platform callback method!");
        System.out.println("receive from bank callback:"+params);
        logger.info("response from Bank"+params);
            //更新RouteMsg的result状态
        RouteMsg routeMsg=GsonUtil.toBean(params,RouteMsg.class);
        String result=routeMsg.getResult();

        //routeMsgService.updateRouteMsgString(routeMsg,result);
        int i=routeMsgService.updateRouteMsgInfodb(routeMsg,result);
        String updateIntodbResult;
        if (i == 1) {
            updateIntodbResult = " platform update status into sourceMsg db success!";
        } else {
            updateIntodbResult = "platform update status into sourceMsg fail!";
        }
        logger.info(updateIntodbResult);
        logger.info(RestClient.restTemplate(channelCallbackUrl,params));

    }

    @RequestMapping(value="/sendToClientChannel",method=RequestMethod.POST)
    @ResponseBody
    public void sendToClientChannel(@RequestBody String params ){
        if(needInitRedis==true){
            logger.info("sendToClientChannel from platform");

        }
    }
    @RequestMapping( value = "/publish", method = RequestMethod.POST)
    @ResponseBody
    public String publishOps(@RequestBody String message) {
       /* AuthUser authUser = (AuthUser) redisTemplate.opsForValue().get(Constants.PRE_TOKEN + token);
        if (Objects.isNull(authUser)) {
            return null;
        }*/
        JedisPool JEDIS_POOL=redisConfig.redisPoolFactory();
        final Jedis publisherJedis = JEDIS_POOL.getResource();
        //主线程：发布消息到CHANNEL_NAME频道上
        new Publisher(publisherJedis, PLATFORM_CHANNEL_NAME_Processing).startPublish(message);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread();
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("发布：");
                //发布消息
                publisherJedis.publish(PLATFORM_CHANNEL_NAME_Processing, "悟空");
                publisherJedis.publish(PLATFORM_CHANNEL_NAME_Processing, "八戒");
                publisherJedis.publish(PLATFORM_CHANNEL_NAME_Processing, message);
            }
        }).start();
        publisherJedis.close();
        return "ok";


    }

    @RequestMapping( value = "/subscribe", method = RequestMethod.POST)
    public String subscribeOps(){
        JedisPool JEDIS_POOL=redisConfig.redisPoolFactory();
        Jedis subscriberJedis = JEDIS_POOL.getResource();
        SubThread subThread=new SubThread(JEDIS_POOL,PLATFORM_CHANNEL_NAME);
        subThread.start();

        Subscriber subscriber = new Subscriber();
        //订阅线程：接收消息
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.info("Subscribing to "+PLATFORM_CHANNEL_NAME+". This thread will be blocked.");
                    //使用subscriber订阅CHANNEL_NAME上的消息，这一句之后，线程进入订阅模式，阻塞。
                    subscriberJedis.subscribe(subscriber, PLATFORM_CHANNEL_NAME);
                    // 订阅得到信息在lister的onMessage(...)方法中进行处理
                    // 订阅多个频道
                    //subscriberJedis.psubscribe(listener, new String[]{"process*"});// 使用模式匹配的方式设置频道

                    //当unsubscribe()方法被调用时，才执行以下代码
                    logger.info("Subscription ended.");
                } catch (Exception e) {
                    logger.error("Subscribing failed.", e);
                }
            }
        }).start();

        //Unsubscribe
        //subscriber.unsubscribe();
        subscriberJedis.close();
        return "ok";
    }
    @RequestMapping( value = "/subscribeBankProcessed", method = RequestMethod.POST)
    public String subscribeBankProcessed(){
        JedisPool JEDIS_POOL=redisConfig.redisPoolFactory();
        Jedis subscriberJedis = JEDIS_POOL.getResource();
        SubThread subThread=new SubThread(JEDIS_POOL,PLATFORM_CHANNEL_NAME_Processed);
        subThread.start();

        Subscriber subscriber = new Subscriber();
        //订阅线程：接收消息
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.info("Subscribing to "+PLATFORM_CHANNEL_NAME_Processed+". This thread will be blocked.");
                    //使用subscriber订阅CHANNEL_NAME上的消息，这一句之后，线程进入订阅模式，阻塞。
                    subscriberJedis.subscribe(subscriber, PLATFORM_CHANNEL_NAME_Processed);
                    // 订阅得到信息在lister的onMessage(...)方法中进行处理
                    // 订阅多个频道
                    //subscriberJedis.psubscribe(listener, new String[]{"process*"});// 使用模式匹配的方式设置频道

                    //当unsubscribe()方法被调用时，才执行以下代码
                    logger.info("Subscription ended.");
                } catch (Exception e) {
                    logger.error("Subscribing failed.", e);
                }
            }
        }).start();

        //Unsubscribe
        //subscriber.unsubscribe();
        subscriberJedis.close();
        return "ok";
    }
    @RequestMapping( value = "/subscribePlatformProcessing", method = RequestMethod.POST)
    public String subscribeOpsPlatformProcessing(){
        JedisPool JEDIS_POOL=redisConfig.redisPoolFactory();
        Jedis subscriberJedis = JEDIS_POOL.getResource();
        SubThread subThread=new SubThread(JEDIS_POOL,PLATFORM_CHANNEL_NAME_Processing);
        subThread.start();

        Subscriber subscriber = new Subscriber();
        //订阅线程：接收消息
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.info("Subscribing to "+PLATFORM_CHANNEL_NAME_Processing+". This thread will be blocked.");
                    //使用subscriber订阅CHANNEL_NAME上的消息，这一句之后，线程进入订阅模式，阻塞。
                    subscriberJedis.subscribe(subscriber, PLATFORM_CHANNEL_NAME_Processing);
                    // 订阅得到信息在lister的onMessage(...)方法中进行处理
                    // 订阅多个频道
                    //subscriberJedis.psubscribe(listener, new String[]{"process*"});// 使用模式匹配的方式设置频道

                    //当unsubscribe()方法被调用时，才执行以下代码
                    logger.info("Subscription ended.");
                } catch (Exception e) {
                    logger.error("Subscribing failed.", e);
                }
            }
        }).start();

        //Unsubscribe
        //subscriber.unsubscribe();
        subscriberJedis.close();
        return "ok";
    }

    /**
     * 月统计查询,1月多少单,2月多少单....
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sumOrderByMonth", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo sumOrderByMonth(HttpServletRequest request) throws Exception {
        String year=request.getParameter("year");

        List<Map<String,Object>> list=routeMsgService.getOrderNumByMonth(year);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setTotal(list.size());
        resultInfo.setRows(list);


        return resultInfo;

    }

    /**
     * 月统计查询,1月多少单,2月多少单....
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getOrderNumByMonth", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getOrderNumByMonth(HttpServletRequest request) throws Exception {
        String year=request.getParameter("year");

        List<Map<String,Object>> list=routeMsgService.getOrderNumByMonth(year);


        return list;

    }

    /**
     * 天统计查询,当月1号多少单,2号多少单,3号多少单....
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getOrderNumByThisMonth", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String,String>> getOrderNumByThisMonth(HttpServletRequest request) throws Exception {

        String month=request.getParameter("month");
        List<Map<String,String>> list=routeMsgService.getOrderByThisMonth(month);


        return list;

    }
    /**
     * 渠道统计查询,MaShangFinTech: 多少件   XWBank: 多少件
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/classificationByChannel", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> queryclassificationByChannel(HttpServletRequest request) throws Exception {
        List<Map<String, Object>> list=null;
         try {
              list= routeMsgService.classificationByChannel();
         }catch(Exception e){
             logger.debug(e.getMessage());
             e.printStackTrace();
         }


        return list;

    }


    /**
     *  城市统计查询,北京: 多少件   天津: 多少件
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/classificationByCiy", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> queryclassificationByCity(HttpServletRequest request) throws Exception {

        logger.info("发向场景集市平台的城市统计查询参数：");

        List<Map<String,Object>> list=routeMsgService.classificationByCity();



        return list;

    }
    /**
     * 进件查询
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryJINJIAN")
    @ResponseBody
    public ResultInfo queryJINJIAN(HttpServletRequest request) throws Exception {
        String month=request.getParameter("month");
        String page=request.getParameter("page");
        String rows=request.getParameter("rows");
        int page2=0;
        if(page!=null){
            page2=Integer.parseInt(request.getParameter("page"));
        }
        int rows2=10;
        if(rows!=null){
            rows2=Integer.parseInt(request.getParameter("rows"));
        }


        logger.info("发向场景集市平台的查询参数month:"+month+"-page:"+page+"-rows:"+rows );

        QueryMsg queryMsg=new QueryMsg();
        queryMsg.setPage(page2);
        queryMsg.setRows(rows2);
        queryMsg.setBeginRecord((page2-1)*rows2);
        List<RouteMsg> routeMsgList=routeMsgService.getAllNeedProcessList1(queryMsg);
        for(int i=0;i<routeMsgList.size();i++){
            String msg=routeMsgList.get(i).getMsg();
            Student student= (Student) JaXmlBeanUtil.parseXmlToBean(Student.class,msg);
            routeMsgList.get(i).setMsg("sno:"+student.getSno()+"\n"+"sname:"+student.getName()+"\n"+
            "sex:"+student.getSex()+"\narea:"+student.getArea()+"\nIdcard:"
            +student.getIdcard()+"\nphone:"+student.getPhone()+"\nproduct:"+student.getProduct()+"\nage:"
            +student.getAge()+"\namount:"+student.getAmount());
            String material=routeMsgList.get(i).getMaterial();
            routeMsgList.get(i).setMaterial(Base64.decodeToString(material));
        }
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setTotal(routeMsgList.size());
        resultInfo.setRows(routeMsgList);


        return resultInfo;

    }

    /**
     * 进件查询
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryCurrentLoanList", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> queryCurrentLoanList(HttpServletRequest request) throws Exception {
         int page=Integer.parseInt(request.getParameter("page"));
         int rows=Integer.parseInt(request.getParameter("rows"));
        logger.info("queryCurrentLoanList 参数:");

        QueryMsg queryMsg=new QueryMsg();
       queryMsg.setPage(page);
       queryMsg.setRows(rows);
        queryMsg.setBeginRecord((page-1)*rows);
        List<Map<String, Object>> routeMsgList=routeMsgService.getAllNeedProcessList(queryMsg);


        return routeMsgList;

    }
    /**
     * 路由查询
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryHandRoute", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public List<RouteRuleBean> queryRoute(HttpServletRequest request) throws Exception {

        logger.info("手工路由查询参数：");

        List<RouteRuleBean> handRouteRules=routeRuleService.getHandRouteRule();

        return handRouteRules;



    }
    /**
     * 路由操作
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/opsHandRoute", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String opsRoute(HttpServletRequest request) throws Exception {
        logger.info("设置手工路由：");
        String result="";
        String sourceChannel=request.getParameter("sourceChannel");
        String bankId=request.getParameter("bankId");
        logger.info("New Route:sourceChannel["+sourceChannel+"] to destBankId(1:XWBank,2:HengShuiBank)["+bankId+"]");
        RouteRuleBean routeRuleBean=new RouteRuleBean();
        routeRuleBean.setDstBankId(bankId);
        routeRuleBean.setSourceChannel(sourceChannel);
        routeRuleBean.setRouteType("hand");
        int i=routeRuleService.setHandRouteRule(routeRuleBean);
        if(i==1){
            result="success";
        }
        if(i==0){
            result="fail";
        }
        return result;

    }


}

