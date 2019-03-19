package com.springboot.model.route;

import lombok.Data;

@Data
public class RouteRuleBean {
    String sourceChannel;
    String minValue;
    String max;
    String routeType;
    String dstBankId;
    String dstBankName;
    String dstBankIp;
    String dstBankPort;

}
