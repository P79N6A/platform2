package com.springboot.model.route.bean;

import lombok.Data;

import java.util.List;
@Data
public class AreaRoute extends Route {
    List<String> areaList;
}
