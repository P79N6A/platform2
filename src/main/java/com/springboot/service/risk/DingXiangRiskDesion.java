package com.springboot.service.risk;

import com.alibaba.fastjson.JSON;
import com.dingxianginc.ctucommon.client.CtuClient;
import com.dingxianginc.ctucommon.client.model.CtuRequest;
import com.dingxianginc.ctucommon.client.model.CtuResponse;
import com.dingxianginc.ctucommon.client.model.RiskLevel;


import java.util.HashMap;
import java.util.Map;

public class DingXiangRiskDesion {

        /**
         * 风控引擎url
         **/
        public static final String url = "http://dxhz4811.dingxiang-inc.com:7009/ctu/event.do";
        /**
         * 应用AppId，公钥
         **/
        public static final String appId = "8063aa3ae5cfe6e53bf078f48d93b015";
        /**
         * 应用AppSecret，私钥
         **/
        public static final String appSecret = "3177c764b03ffca26093791fe2ae38b6";

        public static void checkRisk() throws Exception {
            /**业务请求数据**/
            Map<String, Object> data = new HashMap<>();
            data.put("const_id", "exxxxxxwbZsF1PqoflWOyhKLIhAzw9X1"); // 设备指纹token，端上获取 传入后台
            data.put("user_id", 456799324); // 用户ID
            data.put("phone_number", "18519121911"); // 手机号
            data.put("source", 2); // 登录来源
            data.put("ip", "124.204.79.161"); // 请求的ip地址

            data.put("activity_id", 1); // 活动ID
            data.put("register_date", "注册时间，时间格式2017-09-27 10:09:20"); //用户注册时间
            data.put("ext_answer_end_date","答题结束时间，时间格式2017-09-27 10:09:20"); //答题结束时间
            data.put("ext_answer_start_date", "答题开始时间，时间格式2017-09-27 10:09:20"); //答题开始时间
            data.put("ext_open_id", "jijunwei6888"); //微信登录id
            data.put("ext_user_level", 5); //会员等级
            data.put("ext_prov_name", "北京市"); //手机所属省份

            /**创建一个请求数据实例**/
            CtuRequest request = new CtuRequest();
            /**设置产品编码**/
            request.setAppCode("demo_product");
            /**设置事件编码**/
            request.setEventCode("demo_event");
            /**设置该次风控请求的业务数据**/
            request.setData(data);
            request.setFlag("activity_" + System.currentTimeMillis());
            /**创建一个客户端实例**/
           // CtuClient client = new CtuClient(url, appId, appSecret);
            CtuClient client = new CtuClient(url,appId,appSecret, 2000, 2000,2000);
            /** CtuClient client = new CtuClient(url,appKey,appSecret, connectTimeout, connectionRequestTimeout,socketTimeout)
             用户可以自定义超时设置
             connectTimeout，connectionRequestTimeout，socketTimeout 单位：毫秒
             默认超时设置均为2000毫秒
             **/
            /**向风控引擎发送请求，获取引擎返回的结果**/
            CtuResponse response = client.checkRisk(request);
            if (RiskLevel.ACCEPT.equals(response.getResult().getRiskLevel())) {
                System.out.printf(JSON.toJSONString(response));
                //... 业务代码，当前请求没有风险
            } else if (RiskLevel.REVIEW.equals(response.getResult().getRiskLevel())) {
                System.out.printf(JSON.toJSONString(response));
                //... 业务代码，当前请求有一定风险，建议复审
            } else if (RiskLevel.REJECT.equals(response.getResult().getRiskLevel())) {
                System.out.printf(JSON.toJSONString(response));
                //... 业务代码，当前请求有风险，建议拒绝
            }
        }

    public static void main(String args[]){
        try {
            checkRisk();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
