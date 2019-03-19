package com.springboot.mapper;

import com.springboot.model.route.QueryMsg;
import com.springboot.model.route.RouteMsg;
import com.springboot.model.route.SourceMsg;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;
import java.util.Map;

@Mapper
@CacheConfig(cacheNames = "routeMsg")
public interface RouteMsgMapper {


    @Delete("delete from routemsgbean where id=#{id}")
    void deleteRouteMsgById(int id);

    @Update("update routemsg set result=#{result} where channelSeq=#{routeMsg.channelSeq}")
    int updateRouteMsg(@Param("routeMsg") RouteMsg routeMsg,@Param("result") String result);
    @Update("update routemsg set result=#{result} where id=#{id}")
    int updateRouteMsgById(int id,String result);


    @Update("update routemsgbean set result=#{result} where id=#{id}")
    int updateRouteMsgBeanById(int id,String result);
    @Update("update routemsgbean set result=#{result} where channelSeq=#{routeMsg.channelSeq}")
    int updateRouteMsgBean(@Param("routeMsg") RouteMsg routeMsg,@Param("result") String result);


    /*@Insert("insert into sourcemsg(msg,material,result) "
            + " VALUES(#{msg},#{material},#{result})")
    int addRouteMsgString(String sourceMsg);*/
    @Insert("insert into routemsgbean(destBankCode,platformCode,channelDate,channelTime,channelSeq,channelCode,msg,material,result) "
            + " VALUES(#{destBankCode},#{platformCode},#{channelDate},#{channelTime},#{channelSeq},#{channelCode},#{msg},#{material},#{result})")
    int addRouteMsgBean(RouteMsg rMsg);
    @Select("select * from routemsgbean where id=#{id}")
    @Results(id = "routemsg", value = { @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "channelDate", column = "channelDate", javaType = String.class),
            @Result(property = "channelTime", column = "channelTime", javaType = String.class),
            @Result(property = "channelSeq", column = "channelSeq", javaType = String.class),
            @Result(property = "channelCode", column = "channelCode", javaType = String.class),
            @Result(property = "msg", column = "msg", javaType = String.class),
            @Result(property = "material", column = "material", javaType = String.class),
            @Result(property = "destBankCode", column = "destBankCode", javaType = String.class),
            @Result(property = "platformCode", column = "platformCode", javaType = String.class),
            @Result(property = "result", column = "result", javaType = String.class)
    })
    SourceMsg queryRouteMsgById(int id);
    @Select("select * from routemsg where id=#{id}")
    @Results(id = "routemsgString", value = { @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "msg", column = "msg", javaType = String.class),
            @Result(property = "material", column = "material", javaType = String.class),
            @Result(property = "result", column = "result", javaType = String.class)
    })
    String queryRouteMsgStringById(int id);

    @Select("select channelCode,channelTime,channelSeq,channelDate,msg,material,result from routemsgbean where  (result is null or or result='') limit #{beginRecord},#{rows}")
    List<Map<String, Object>> getAllNeedProcessList(QueryMsg queryMsg);
    @Select("select channelCode,channelTime,channelSeq,channelDate,msg,material,result from routemsgbean where  (result is null or result='') limit #{beginRecord},#{rows}")
    List<RouteMsg> getAllNeedProcessList1(QueryMsg queryMsg);
    /*<resultMap id="BaseResultMap" type="map">
        <result column="ID" property="applyId" jdbcType="VARCHAR"/>
        <result column="NAME" property="renter" jdbcType="VARCHAR"/>
        <result column="APPLY_TIME" property="orderDate" jdbcType="DATE"/>
        <result column="REPAY_DATE" property="orderDate" jdbcType="DATE"/>
        <result column="ACTUAL_AMOUNT" property="loanAmount" jdbcType="DECIMAL"/>
        <result column="CURRENT_AMOUNT" property="repayAmount" jdbcType="DECIMAL"/>
        <result column="RENT_STAUTS" property="orderState" jdbcType="INTEGER"/>
        <result column="PAY_STATUS" property="repayState" jdbcType="INTEGER"/>
    </resultMap>

    <select id="currentLoanList" resultMap="BaseResultMap">
    SELECT srti.ID,srb.`NAME`,srti.APPLY_TIME,FORMAT(srti.ACTUAL_AMOUNT,2) loanAmount,srti.RENT_STAUTS FROM slm_renter_tenancy_info srti LEFT JOIN slm_renter_baseInfo srb ON srti.RENTER_ID=srb.RENTER_ID
    WHERE  srti.RENT_STAUTS IN (30,17,20) AND srti.ISVALID=1
    </select>

    <select id="threeDaysRepaymentList" resultMap="BaseResultMap">
    SELECT srti.ID,srb.`NAME`,sfrp.REPAY_DATE,FORMAT(sfrp.CURRENT_AMOUNT,2) repayAmount,sfrp.PAY_STATUS,srti.RENT_STAUTS FROM slm_ffq_repayment_plan sfrp LEFT JOIN slm_renter_tenancy_info srti ON sfrp.APPLY_SUB_NO=srti.ID
    LEFT JOIN slm_renter_baseInfo srb ON srti.RENTER_ID=srb.RENTER_ID
    WHERE  DATEDIFF(sfrp.REPAY_DATE,NOW()) BETWEEN 0 AND 3
    </select>

    <select id="classificationByCity" resultMap="BaseResultMap">
    SELECT
    srti.ID,
    SUM(srti.ACTUAL_AMOUNT) cityAmount,
    sr.`NAME` AS cityName
    FROM
    slm_renter_tenancy_info srti
    LEFT JOIN slm_housing sh ON srti.HOUSE_ID = sh.ID
    LEFT JOIN slm_community sc ON sh.COMPOUND_ID = sc.ID LEFT JOIN slm_region sr ON sc.CITY = sr.ID
            WHERE
    srti.ISVALID = 1
    AND srti.RENT_STAUTS BETWEEN 20
    AND 27 GROUP BY sr.`NAME`
    </select>

    <select id="classificationByFlat" resultMap="BaseResultMap">
            (SELECT
    srti.ID,
    SUM(srti.ACTUAL_AMOUNT) flatAmount,
    sfi.COMPANY_SHORT_NAME flatName
    FROM
    slm_renter_tenancy_info srti
    LEFT JOIN slm_housing sh ON srti.HOUSE_ID = sh.ID
    LEFT JOIN slm_flat_info sfi ON sh.FLAT_ID = sfi.FLAT_ID
            WHERE
    srti.ISVALID = 1
    AND srti.RENT_STAUTS BETWEEN 20
    AND 27
    GROUP BY
    sfi.COMPANY_ALL_NAME) ORDER BY flatAmount DESC LIMIT 5
    </select>*/


}
