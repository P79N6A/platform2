package com.springboot.model.route.bean;

import lombok.Data;

@Data
public class AgeRoute extends Route{
    int minAge;
    int maxAge;
}
