package com.springboot.model.route.condition;

import com.springboot.model.route.bean.AgeRoute;

public class AgeCondition extends Condition {
    AgeRoute ageRoute;
    public boolean isInAgeArea(int age){
        return ((age>ageRoute.getMinAge()) &&(age<=ageRoute.getMaxAge()))?true:false;

    }

}
