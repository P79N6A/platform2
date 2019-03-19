package com.springboot.model.route.condition;

import com.springboot.model.route.bean.AmountRoute;
import com.springboot.model.route.bean.AreaRoute;


public class AreaCondition  extends Condition{
    AreaRoute areaRoute;

    public boolean isInAreaList(String area){
        return areaRoute.getAreaList().contains(area)?true:false;

    }
}
