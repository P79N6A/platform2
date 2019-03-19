package com.springboot.model.route.condition;

import com.springboot.model.route.bean.AgeRoute;
import com.springboot.model.route.bean.AmountRoute;
import com.springboot.model.route.bean.Route;
import com.springboot.model.route.dst.DstRoute;
import com.springboot.service.impl.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AmountCondition extends Condition{
    AmountRoute amountRoute;
    String source;
    String dst;
    List<DstRoute> dstRoutes;
    @Autowired
    private RedisServiceImpl service;
    public AmountCondition(String source, List<DstRoute> dstRoutes){
        this.source=source;
        this.dstRoutes=dstRoutes;
    }
    public boolean isInAmountArea(int amount){
        return ((amount>amountRoute.getMinAmount()) &&(amount<=amountRoute.getMaxAmount()))?true:false;

    }
    public DstRoute getOneBank(double amount,List<DstRoute> dstRoutes){
        DstRoute dstRoute;
        if(source.equals("MaShangFinTech")&& amount>0 && amount<=30000){
           dst="XWBank";
            service.get("amount:0:30000:"+source);

         }
         return null;
    }


}
