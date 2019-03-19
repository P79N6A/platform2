package com.springboot.model.route;

import com.springboot.service.route.RouteRuleService;
import com.springboot.service.route.RouteService;
import com.springboot.util.ChannelUtil;
import com.springboot.util.SpringUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Data
public class RouteRule {
    Logger logger= LoggerFactory.getLogger(RouteRule.class);
    @Autowired
    private RouteRuleService routeRuleService;
    /**
     * 产品
     */
    Map<String,HashMap<String,List<String>>> routeByProduct=new HashMap<String,HashMap<String,List<String>>>();
    /**
     * 风险等级
     */

    Map<String,HashMap<String,List<String>>> routeByRiskDegree=new HashMap<String,HashMap<String,List<String>>>();
    /**
     * 地域
     */
    Map<String,HashMap<String,List<String>>> routeByArea=new HashMap<String,HashMap<String,List<String>>>();
    /**
     * 年龄
     */

    Map<String,HashMap<String,List<String>>> routeByAge=new HashMap<String,HashMap<String,List<String>>>();

    /**
     * 金额
     */
    Map<String,HashMap<String,List<String>>> routeByAmount=new HashMap<String,HashMap<String,List<String>>>();


    /**
     * 权重
     */
    Map<String,HashMap<String,List<String>>> routeByWeight=new HashMap<String,HashMap<String,List<String>>>();

    /**
     * 手工配置
     */
    Map<String,HashMap<String,List<String>>> routeByHand=new HashMap<String,HashMap<String,List<String>>>();

    /**
     * 随机
     */

    Map<String,HashMap<String,List<String>>> routeByRandom=new HashMap<String,HashMap<String,List<String>>>();

    public void RouteRule(){

    }
    public void initRouteByReadConfigFile(){
        File f=new File("routerule.config");
        try {
            FileReader fr = new FileReader(f);
            String line=null;

        }catch(Exception e){
            logger.info(e.getMessage());
        }
    }
    public void initRouteByWeight(){

        HashMap<String,List<String>> destByHand=new HashMap<String,List<String>>();
        List<String> dest=new ArrayList<String>();
        //weight:DestBank:IP:Port   weight:越高，先选择
        dest.add("3:HengShuiBank:127.0.0.1:5002");
        dest.add("2:XWBank:127.0.0.1:5001");
        dest.add("1:BaoTouBank:127.0.0.1:5003");
        destByHand.put("XWBank",dest);
        List<String> bankDest=new ArrayList<String>();
        bankDest.add("1:HengShuiBank:127.0.0.1:5002");
        bankDest.add("2:XWBank:127.0.0.1:5001");
        destByHand.put("MaShangFinTech",bankDest);
    }
    public void initRouteByHand(){
        HashMap<String,List<String>> destByHand=new HashMap<String,List<String>>();
        List<String> dest=new ArrayList<String>();
        //DestBank:IP:Port
        dest.add("XWBank:127.0.0.1:5001");
        destByHand.put("MaShangFinTech",dest);
        List<String> bankDest=new ArrayList<String>();
        bankDest.add("HengShuiBank:127.0.0.1:5002");
        destByHand.put("XWBank",bankDest);

        routeByHand.put("hand",destByHand);
    }

    public String getRouteByHand(String channel,String rule){
        if(routeRuleService==null){
           routeRuleService = SpringUtil.getBean(RouteRuleService.class);
        }
        logger.info("channel: "+channel);
        List<RouteRuleBean> handRouteRules=routeRuleService.getHandRouteRule();
        HashMap<String,List<String>> destByHand=new HashMap<String,List<String>>();
        for(int i=0;i<handRouteRules.size();i++){
            List<String> dest=new ArrayList<String>();
            //DestBank:IP:Port
            RouteRuleBean r=handRouteRules.get(i);
            String d1=r.getDstBankName()+":"+r.getDstBankIp()+":"+r.getDstBankPort();
            dest.add(d1);
            destByHand.put(r.getSourceChannel(),dest);

        }



        routeByHand.put("hand",destByHand);
        String dest1=null;
        if(rule.equals("hand")){
            HashMap<String,List<String>> destByHand1=routeByHand.get("hand");

            List<String> destBank=destByHand1.get(channel);
            if(!destBank.isEmpty()){
                dest1=destBank.get(0);
            }

        }
       return dest1;
    }

    public String getRouteByWeight(String channelNode,String rule){
        String dest=null;
        if(rule.equals("weight")){
            HashMap<String,List<String>> destByHand=routeByWeight.get("weight");
            List<String> destBank=destByHand.get(channelNode);
            if(!destBank.isEmpty()){
                dest=destBank.get(0);
            }

        }
        return dest;
    }


}
