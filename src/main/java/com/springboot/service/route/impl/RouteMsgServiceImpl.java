package com.springboot.service.route.impl;

import com.springboot.mapper.RouteMsgMapper;
import com.springboot.model.route.QueryMsg;
import com.springboot.model.route.RouteMsg;
import com.springboot.service.route.RouteMsgService;
import com.springboot.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class RouteMsgServiceImpl implements RouteMsgService{
    @Autowired
    RouteMsgMapper routeMsgMapper;
    @Override
    public int addRouteMsg(String routeMsg){
        //int i=routeMsgMapper.addRouteMsgString(routeMsg);
        return 0;
    }

    @Override
    public int updateRouteMsgString(RouteMsg routeMsg,String result){
        int i=0;/*=routeMsgMapper.updateRouteMsg(routeMsg,result);*/

        return i;
    }
    @Override
    public int addRouteMsgBean(RouteMsg routeMsg){
        int i=routeMsgMapper.addRouteMsgBean(routeMsg);
        return i;
    }
    @Override
    public int updateRouteMsgInfodb(RouteMsg routeMsg,String result){
        int i=routeMsgMapper.updateRouteMsgBean(routeMsg,result);
        return i;
    }

    //计算件数所占比例
    private static List<Map<String, Object>> percentageOfAmount(List<Map<String, Object>> list){
        BigDecimal bigDecimal = new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            bigDecimal.add(new BigDecimal(list.get(i).get("cityAmount").toString()));
        }
        for (int i = 0; i < list.size(); i++) {
            BigDecimal percent = new BigDecimal(list.get(i).get("cityAmount").toString()).divide(bigDecimal)
                    .setScale(4, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
            list.get(i).put("percent",percent);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> classificationByCity() {
        /*List<Map<String, Object>> relist = routeMsgMapper.classificationByCity();*/
        List<Map<String, Object>> relist = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("cityAmount","23547");
        map.put("cityName","北京市");
        Map<String, Object> map1 = new HashMap<>();
        map1.put("cityAmount","12337");
        map1.put("cityName","上海市");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("cityAmount","6042");
        map2.put("cityName","天津市");
        Map<String, Object> map3 = new HashMap<>();
        map3.put("cityAmount","5477");
        map3.put("cityName","重庆市");
        Map<String, Object> map4 = new HashMap<>();
        map4.put("cityAmount","1377");
        map4.put("cityName","河北省");
        relist.add(map);
        relist.add(map1);
        relist.add(map2);
        relist.add(map3);
        relist.add(map4);
        return relist;
    }
 /*   @Override
    public List<Map<String, Object>> classificationByChannel() {
        List<Map<String, Object>> desensitization = desensitization(routeMsgMapper.classificationByChannel());

        return desensitization;
    }*/
    //业务信息脱敏
    private static List<Map<String, Object>> desensitization(List<Map<String, Object>> list){
        for (int i = 0; i < list.size(); i++) {
            String oldRenter = list.get(i).get("msg").toString();
            String substring = oldRenter.substring(0, 1);
            StringBuffer stringBuffer = new StringBuffer(substring);
            for (int j = 1; j < oldRenter.length(); j++) {
                if (j!=oldRenter.length()){
                    stringBuffer.append("*");
                }
            }
            list.get(i).put("msg",stringBuffer.toString());
            list.get(i).put("channelDate", DateUtil.date2String((Date) list.get(i).get("channelDate")));
        }
        return list;
    }


    private static List<String> departMonthToWeek(){
        List<String> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        //比当前月份少1
        //date表示日期，day表示天数，所以date与day_of_month相同
        list.add(cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH));
        for (int i = 0; i <3; i++) {
            cal.add(Calendar.DAY_OF_MONTH,-7);
            int month = cal.get(Calendar.MONTH)+1;
            int day = cal.get(Calendar.DATE);
            String date = year+"/"+month+"/"+day;
            list.add(date);
        }
        return list;
    }

    private static List<String> departMonthToDay(){
        List<String> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        //比当前月份少1
        //date表示日期，day表示天数，所以date与day_of_month相同
        list.add(cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH));
        for (int i = 0; i <3; i++) {
            cal.add(Calendar.DAY_OF_MONTH,-7);
            int month = cal.get(Calendar.MONTH)+1;
            int day = cal.get(Calendar.DATE);
            String date = year+"/"+month+"/"+day;
            list.add(date);
        }
        return list;
    }

    @Override
    public List<Map<String, String>> getOrderByThisMonth(String month) {
        List<Map<String, String>> arrayList = new ArrayList<>();
        /*List<String> list = departMonthToWeek();*/
        /*List<String> list=departMonthToDay()*/;
        List<String> list=new ArrayList<>();
        list.add("20190309");
        list.add("20190308");
        list.add("20090310");
        for (int i = list.size(); i > 0; --i) {
            /*String orderNum = routeMsgMapper.getOrderByThisMonth(list.get(i-1));*/
            int orderNum=new Random().nextInt(1000);
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("orderNum",new Integer(orderNum).toString());
            hashMap.put("date",list.get(i-1));
            arrayList.add(hashMap);
        }
        return arrayList;
    }

    @Override
    public List<Map<String,Object>> getOrderNumByMonth(String year){
        List<Map<String, Object>> arrayList = new ArrayList<>();
        String[] months={"Jan","Feb","Mar","April","May","June","July","Aug","Sep","Oct","Nov","Dec"};
        Map<String, Object> hashMap = new HashMap<>();
        for (int i = 0; i <12; i++) {
            int orderNum=new Random().nextInt(1000);

            hashMap.put(months[i],orderNum);
        }
        arrayList.add(hashMap);
        /*for (int i = 0; i <12; i++) {
            int orderNum=new Random().nextInt(1000);
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("orderNum",orderNum);
            hashMap.put("month",i);
            arrayList.add(hashMap);
        }*/
        return  arrayList;
    }
    @Override
    public List<Map<String, Object>> classificationByChannel() {
        //List<Map<String, Object>> list = routeMsgMapper.classificationByChannel();
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        String[] channels={"MaShangFinTech","XWBank"};
        /*String s = routeMsgMapper.getChannelAMount(channels[0]);*/
        String s="200";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("orderAmount",s);
        hashMap.put("channel",channels[0]);
        list.add(hashMap);

        /*s = routeMsgMapper.getChannelAMount(channels[1]);*/
        String s1="300";
        HashMap<String,Object> hashMap2 = new HashMap<>();
        hashMap2.put("orderAmount",s1);
        hashMap2.put("channel",channels[1]);
        list.add(hashMap2);
        return list;
    }
    @Override
    public List<Map<String, Object>> getAllNeedProcessList(QueryMsg queryMsg){
        return routeMsgMapper.getAllNeedProcessList(queryMsg);
    }
    @Override
    public List<RouteMsg> getAllNeedProcessList1(QueryMsg queryMsg){
        return routeMsgMapper.getAllNeedProcessList1(queryMsg);
    }
}
