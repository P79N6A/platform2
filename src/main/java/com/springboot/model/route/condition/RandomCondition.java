package com.springboot.model.route.condition;

import com.springboot.model.route.dst.DstRoute;
import lombok.Data;

import java.util.List;
import java.util.Random;

@Data
public class RandomCondition extends Condition{
    boolean random=false;
    public RandomCondition(boolean isRandom){
        this.random=isRandom;
    }
    public DstRoute getOneDstBank(List<DstRoute> dstRoutes){
        int size=dstRoutes.size();
        int index=new Random().nextInt(size);
        return dstRoutes.get(index);
    }
}
