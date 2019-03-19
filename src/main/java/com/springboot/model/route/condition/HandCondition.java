package com.springboot.model.route.condition;

import com.springboot.model.route.dst.DstRoute;
import lombok.Data;

@Data
public class HandCondition extends Condition{
    boolean hand=false;
    String source;
    public HandCondition(String source,boolean isHand){
        this.hand=isHand;
        this.source=source;
    }

    public DstRoute getDstBank(){
        return null;
    }
}
