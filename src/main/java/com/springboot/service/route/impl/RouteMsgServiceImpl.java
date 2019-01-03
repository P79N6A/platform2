package com.springboot.service.route.impl;

import com.springboot.mapper.RouteMsgMapper;
import com.springboot.model.route.RouteMsg;
import com.springboot.service.route.RouteMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteMsgServiceImpl implements RouteMsgService{
    @Autowired
    RouteMsgMapper routeMsgMapper;
    @Override
    public int addRouteMsg(RouteMsg routeMsg){
        int i=routeMsgMapper.add(routeMsg);
        return i;
    }
}
