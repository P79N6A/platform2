package com.springboot.service.route.impl;

import com.springboot.mapper.RouteRuleMapper;
import com.springboot.model.route.RouteRule;
import com.springboot.model.route.RouteRuleBean;
import com.springboot.model.route.RouteRuleKeyValue;
import com.springboot.service.route.RouteRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteRuleServiceImpl implements RouteRuleService {


    @Autowired
    RouteRuleMapper routeRuleMapper;
    @Override
    public int addRouteRuleKeyValue(RouteRuleKeyValue routeRuleKeyValue){
        int i=routeRuleMapper.addRouteRuleKeyValue(routeRuleKeyValue);
        return i;
    }
    @Override
    public int addRouteRuleBean(RouteRuleBean routeRuleBean){
        int i=routeRuleMapper.addRouteRuleBean(routeRuleBean);
        return i;
    }
    @Override
    public int updateRouteRuleValue(RouteRuleKeyValue routeRuleKeyValue){
        int i=routeRuleMapper.updateRouteRuleValue(routeRuleKeyValue);

        return i;
    }
    @Override
    public int updateRouteRuleKey(RouteRuleKeyValue routeRuleKeyValue){
        int i=routeRuleMapper.updateRouteRuleKey(routeRuleKeyValue);

        return i;
    }

    @Override
    public int updateRouteRuleBean(RouteRuleBean routeRuleBean){
        int i=routeRuleMapper.updateRouteRuleBean(routeRuleBean);
        return i;
    }
    @Override
    public List<RouteRuleBean> getHandRouteRule(){
        return routeRuleMapper.queryRouteRuleBeanListByType("hand");
    }
    @Override
    public int setHandRouteRule(RouteRuleBean routeRuleBean){
        return routeRuleMapper.updateRouteRuleBean(routeRuleBean);
    }

}
