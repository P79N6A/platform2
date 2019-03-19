package com.springboot.model.route.bean;

import lombok.Data;

@Data
public class ProductRoute extends Route {
    String productType;
    String productName;

}
