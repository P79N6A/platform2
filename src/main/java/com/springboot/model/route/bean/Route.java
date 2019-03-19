package com.springboot.model.route.bean;

import com.springboot.model.route.dst.DstRoute;
import lombok.Data;

import java.io.Serializable;

@Data
public  class Route implements Serializable {
    String source;
    String type;
    String key;   //Amount:MaShangFinTech:0:30000->
    String value;//{"dst":"XWBank","IP":"127.0.0.1","port":5001}
    DstRoute dst;
}
