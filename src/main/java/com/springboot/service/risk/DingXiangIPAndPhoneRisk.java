package com.springboot.service.risk;

import com.dingxianginc.dataservice.dxaf.DSClient;
import com.dingxianginc.dataservice.dxaf.constant.ReturnCodeConstant;
import com.dingxianginc.dataservice.dxaf.constant.ReturnMsgEnum;
import com.dingxianginc.dataservice.dxaf.model.DXAFRequest;
import com.dingxianginc.dataservice.dxaf.model.DXAFResponse;
import com.dingxianginc.dataservice.dxaf.model.RiskResult;

import java.util.Date;
import java.util.Map;

public class DingXiangIPAndPhoneRisk {
    /**
     * 服务地址
     **/
    public static final String url = "https://sec2.dingxiang-inc.com/afis/event.do";
    /**
     * 应用AppId，公钥
     **/
    public static final String appId = "503f30a087e8488665ec47d88fa3033c";
    /**
     * 应用AppSecret，私钥
     **/
    public static final String appSecret = "a11dd76413f63b7617db25bdcc71f9ea";

    /**创建一个请求数据实例**/
    //DXAFRequest dxafRequest = new DXAFRequest();
    /**放入请求数据**/
    /*dxafRequest.setTarget("1,2");
    dxafRequest.setPhoneNumber("18519121911");
    dxafRequest.setUserIP("124.204.79.161");*/

    /**创建一个客户端实例**/
    //DSClient dsClient = new DSClient(url, appId, appSecret);
    /**获取结果**/
    //DXAFResponse dxafResponse = dsClient.queryRisk(dxafRequest);

    /*if (ReturnCodeConstant.SUCCESS.equals(dxafResponse.getReturnCode())) {
        Map<String, RiskResult> result = dxafResponse.getResult();
        // 根据请求的target取得对应返回结果
        RiskResult phoneRiskResult = result.get("phone");
        // 得分，大于80分为高风险，建议采取措施；大于60分为中风险，结合具体情况作出处理；小于60分为低风险，10分表示白名单
        int phoneRiskScore = phoneRiskResult.getScore();
        // 是否命中，1表示命中风险；0表示未命中风险；-1表示数据计算过程发生异常，未能计算出有效结果
        int phoneHit = phoneRiskResult.getHit();
        // 数据最后一次的更新时间
        Date phoneUpdateTime = phoneRiskResult.getUpdateTime();

        RiskResult ipRiskResult = result.get("ip");
        int ipRiskScore = ipRiskResult.getScore();
        int ipHit = ipRiskResult.getHit();
        Date ipUpdateTime = ipRiskResult.getUpdateTime();
    } else {
        // ... 根据不同的错误码进行不同的业务处理
        if (ReturnMsgEnum.INVALID_REQUEST_PARAM.getDescription().equals(dxafResponse.getErrMsg())) {

        }
        // ...
    }*/
}
