package com.springboot.service.route;

import com.springboot.model.route.QueryMsg;
import com.springboot.model.route.RouteMsg;

import java.util.List;
import java.util.Map;

public interface RouteMsgService {
    int addRouteMsg(String routeMsg);

    int updateRouteMsgString(RouteMsg routeMsg,String result);

    int addRouteMsgBean(RouteMsg routeMsg);

    int updateRouteMsgInfodb(RouteMsg routeMsg,String result);


    List<Map<String, Object>> getAllNeedProcessList(QueryMsg queryMsg);
    List<RouteMsg> getAllNeedProcessList1(QueryMsg queryMsg);
    /**
     *  当月每天进件数
     * @return
     */
    List<Map<String, String>> getOrderByThisMonth(String month);

    /**
     * 根据渠道统计进件
     * @return
     */
    List<Map<String, Object>> classificationByChannel();

    /**
     * 根据城市统计进件
     * @return
     */
    List<Map<String, Object>> classificationByCity();

    /**
     * 每月进件统计
     */
    List<Map<String,Object>> getOrderNumByMonth(String year);

}
