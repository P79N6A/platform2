package com.springboot.model.route.bean;

import lombok.Data;

@Data
public class AmountRoute extends Route {
    public double minAmount;
    public double maxAmount;
}
