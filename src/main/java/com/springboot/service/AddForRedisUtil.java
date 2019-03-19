package com.springboot.service;

import com.google.gson.Gson;
import com.springboot.model.route.bean.*;
import com.springboot.model.route.dst.DstRoute;
import com.springboot.service.impl.RouteServiceForRedis;
import com.springboot.service.route.impl.RouteServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@CacheConfig(cacheNames = "routeRule")
public class AddForRedisUtil {
    Logger logger= LoggerFactory.getLogger(AddForRedisUtil.class);
    private  RouteServiceForRedis service=new RouteServiceForRedis();
     Gson gson=new Gson();
    public  void addInfoRedis(String str){
        if(str.contains("amount")){
            addAmountRouteInfoForRedis(str);
        }
        if(str.contains("age")){
            addAgeRouteInfoForRedis(str);
        }
        if(str.contains("risk")){
            addRiskRouteInfoForRedis(str);
        }
        if(str.contains("area")){
            addAreaRouteInfoForRedis(str);
        }
        if(str.contains("random")){
            addRandomRouteInfoForRedis(str);
        }
        if(str.contains("hand")){
            addHandRouteInfoForRedis(str);
        }
        if(str.contains("product")){
            addProductRouteInfoForRedis(str);
        }
        if(str.contains("weight")){
            addWeightRouteInfoForRedis(str);
        }

    }
    @Cacheable(key = "#p0")
    public  AmountRoute addAmountRouteInfoForRedis(String str){
        AmountRoute routeModel = new AmountRoute();
        String [] args=str.split(":");
        StringBuffer key=new StringBuffer();
        for(int i=0;i<4;i++){
            if(i==3){
                key.append(args[i]);
            }else {
                key.append(args[i] + ":");
            }

        }

        routeModel.setMinAmount(Double.valueOf(args[1]));
        routeModel.setMaxAmount(Double.valueOf(args[2]));
        routeModel.setKey(key.toString());
        routeModel.setSource(args[3]);
        routeModel.setType("amount");
        DstRoute dr = new DstRoute();
        dr.setDst(args[4]);
        dr.setIp(args[5]);
        dr.setPort(Integer.parseInt(args[6]));
        routeModel.setValue(gson.toJson(dr));
        routeModel.setDst(dr);
        logger.info(routeModel.getType()+":key"+routeModel.getKey()+" value "+routeModel.getValue());
        service.put(routeModel.getKey(), routeModel, -1);
        return routeModel;
    }

    @Cacheable(key = "#p0")
    public  AreaRoute addAreaRouteInfoForRedis(String str){
        AreaRoute routeModel = new AreaRoute();
        String [] args=str.split(":");
        StringBuffer key=new StringBuffer();
        for(int i=0;i<3;i++){
            if(i==2){
                key.append(args[i]);
            }else {
                key.append(args[i] + ":");
            }
        }
        routeModel.setKey(key.toString());
        routeModel.setSource(args[2]);
        routeModel.setType("area");
        DstRoute dr = new DstRoute();
        dr.setDst(args[3]);
        dr.setIp(args[4]);
        dr.setPort(Integer.parseInt(args[5]));

        routeModel.setValue(gson.toJson(dr));
        routeModel.setDst(dr);
        logger.info(routeModel.getType()+":key"+routeModel.getKey()+" value "+routeModel.getValue());
        service.put(routeModel.getKey(), routeModel, -1);
        return routeModel;
    }

    @Cacheable(key = "#p0")
    public  AgeRoute addAgeRouteInfoForRedis(String str){
        AgeRoute routeModel = new AgeRoute();
        String [] args=str.split(":");
        StringBuffer key=new StringBuffer();
        for(int i=0;i<4;i++){
            if(i==3){
                key.append(args[i]);
            }else {
                key.append(args[i] + ":");
            }
        }
        routeModel.setKey(key.toString());
        routeModel.setSource(args[3]);
        routeModel.setType("age");
        DstRoute dr = new DstRoute();
        dr.setDst(args[4]);
        dr.setPort(Integer.parseInt(args[6]));
        dr.setIp(args[5]);
        routeModel.setValue(gson.toJson(dr));
        routeModel.setDst(dr);
        logger.info(routeModel.getType()+":key"+routeModel.getKey()+" value "+routeModel.getValue());
        service.put(routeModel.getKey(), routeModel, -1);
        return routeModel;
    }

    @Cacheable(key = "#p0")
    public  RiskRoute addRiskRouteInfoForRedis(String str){
        RiskRoute routeModel = new RiskRoute();
        String [] args=str.split(":");
        StringBuffer key=new StringBuffer();
        for(int i=0;i<3;i++){
            if(i==2){
                key.append(args[i]);
            }else {
                key.append(args[i] + ":");
            }
        }
        routeModel.setKey(key.toString());
        routeModel.setSource(args[2]);
        routeModel.setType("risk");
        DstRoute dr = new DstRoute();
        dr.setDst(args[3]);
        dr.setIp(args[4]);
        dr.setPort(Integer.parseInt(args[5]));

        routeModel.setValue(gson.toJson(dr));
        routeModel.setDst(dr);
        logger.info(routeModel.getType()+":key"+routeModel.getKey()+" value "+routeModel.getValue());
        service.put(routeModel.getKey(), routeModel, -1);
        return  routeModel;
    }

    @Cacheable(key = "#p0")
    public  HandRoute addHandRouteInfoForRedis(String str){
        HandRoute routeModel = new HandRoute();
        String [] args=str.split(":");
        StringBuffer key=new StringBuffer();
        for(int i=0;i<2;i++){
            if(i==1){
                key.append(args[i]);
            }else {
                key.append(args[i] + ":");
            }
        }
        routeModel.setKey(key.toString());
        routeModel.setSource(args[1]);
        routeModel.setType("hand");
        DstRoute dr = new DstRoute();
        dr.setDst(args[2]);
        dr.setIp(args[3]);
        dr.setPort(Integer.parseInt(args[4]));

        routeModel.setValue(gson.toJson(dr));
        routeModel.setDst(dr);
        logger.info(routeModel.getType()+":key"+routeModel.getKey()+" value "+routeModel.getValue());
        service.put(routeModel.getKey(), routeModel, -1);
        return routeModel;
    }

    @Cacheable(key = "#p0")
    public  RandomRoute addRandomRouteInfoForRedis(String str){
        RandomRoute routeModel = new RandomRoute();
        int index=str.indexOf("{");
        String [] args=str.substring(index+1,str.length()-1).split(",");
        String [] argskey=str.substring(0,index).split(":");
        StringBuffer key=new StringBuffer();
        for(int i=0;i<2;i++){
            if(i==1){
                key.append(argskey[i]);
            }else {
                key.append(argskey[i] + ":");
            }
        }
        routeModel.setKey(key.toString());
        routeModel.setSource(argskey[1]);
        routeModel.setType("random");
        List<DstRoute> drs = new ArrayList<DstRoute>();
        for(int i=0;i<args.length;i++){
            String[] dst=args[i].split(":");
            DstRoute dr=new DstRoute();
            dr.setDst(dst[0]);
            dr.setIp(dst[1]);
            dr.setPort(Integer.parseInt(dst[2]));
            drs.add(dr);
        }


        routeModel.setValue(gson.toJson(drs));
        routeModel.setDstRoutes(drs);
        logger.info(routeModel.getType()+":key"+routeModel.getKey()+" value "+routeModel.getValue());
        service.put(routeModel.getKey(), routeModel, -1);
        return routeModel;
    }

    @Cacheable(key = "#p0")
    public  WeightRoute addWeightRouteInfoForRedis(String str){
        WeightRoute routeModel = new WeightRoute();
        String [] args=str.split(":");
        StringBuffer key=new StringBuffer();
        for(int i=0;i<3;i++){
            if(i==2){
                key.append(args[i]);
            }else {
                key.append(args[i] + ":");
            }
        }
        routeModel.setKey(key.toString());
        routeModel.setSource(args[2]);
        routeModel.setType("weight");
        DstRoute dr = new DstRoute();
        dr.setDst(args[3]);
        dr.setIp(args[4]);
        dr.setPort(Integer.parseInt(args[5]));

        routeModel.setValue(gson.toJson(dr));
        routeModel.setDst(dr);
        logger.info(routeModel.getType()+":key"+routeModel.getKey()+" value "+routeModel.getValue());
        service.put(routeModel.getKey(), routeModel, -1);
        return routeModel;
    }
    @Cacheable(key = "#p0")
    public  ProductRoute addProductRouteInfoForRedis(String str){
        ProductRoute routeModel = new ProductRoute();
        String [] args=str.split(":");
        StringBuffer key=new StringBuffer();
        for(int i=0;i<3;i++){
            if(i==2){
                key.append(args[i]);
            }else {
                key.append(args[i]);
            }
        }
        routeModel.setKey(key.toString());
        routeModel.setSource(args[2]);
        routeModel.setType("product");
        DstRoute dr = new DstRoute();
        dr.setDst(args[3]);
        dr.setIp(args[4]);
        dr.setPort(Integer.parseInt(args[5]));

        routeModel.setValue(gson.toJson(dr));
        routeModel.setDst(dr);
        logger.info(routeModel.getType()+":key"+routeModel.getKey()+" value "+routeModel.getValue());
        service.put(routeModel.getKey(), routeModel, -1);
        return routeModel;
    }
}
