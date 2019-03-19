package com.springboot.service.route;

import com.springboot.model.route.RouteRule;
import com.springboot.model.route.RouteRuleBean;
import com.springboot.model.route.RouteRuleKeyValue;

import java.util.List;

public interface RouteRuleService {
    int addRouteRuleKeyValue(RouteRuleKeyValue routeRuleKeyValue);
    int addRouteRuleBean(RouteRuleBean routeRuleBean);
    int updateRouteRuleValue(RouteRuleKeyValue routeRuleKeyValue);
    int updateRouteRuleKey(RouteRuleKeyValue routeRuleKeyValue);
    int updateRouteRuleBean(RouteRuleBean routeRuleBean);
    List<RouteRuleBean> getHandRouteRule();
    int setHandRouteRule(RouteRuleBean routeRuleBean);

}
