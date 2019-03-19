package com.springboot.mapper;

import com.springboot.model.route.*;
import com.springboot.model.route.bean.Route;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;

@Mapper
@CacheConfig(cacheNames = "routeRuleBean")
public interface RouteRuleMapper {


    @Delete("delete from routerulebean where id=#{id}")
    void deleteRouteRuleById(int id);

 @Delete("delete from routerulebean where  source_channel=#{routeRuleBean.sourceChannel} and routetype=#{routeRuleBean.routeType} and dstBankId=#{routeRuleBean.dstBankId}")
 void deleteRouteRule(@Param("routeRuleBean") RouteRuleBean routeRuleBean);

 @Delete("delete from routerulekeyvalue where routekey=#{routeRuleKeyValue.routeKey} and routetype=#{routeRuleKeyValue.routeType}")
 void deleteRouteRuleKeyValue(@Param("routeRuleKeyValue") RouteRuleKeyValue routeRuleKeyValue);


 @Update("update routerulekeyvalue set routevalue=#{routeRuleKeyValue.routeValue} " +
            "where routekey=#{routeRuleKeyValue.routekey} and routetype=#{routeRuleKeyValue.routeType}")
   int updateRouteRuleValue(@Param("routeRuleKeyValue") RouteRuleKeyValue routeRuleKeyValue);

 @Update("update routerulekeyvalue set routekey=#{routeRuleKeyValue.routeKey} and routetype=#{routeRuleKeyValue.routeType}" +
         "where routevalue=#{routeRuleKeyValue.routeValue}")
 int updateRouteRuleKey(@Param("routeRuleKeyValue") RouteRuleKeyValue routeRuleKeyValue);


    @Update("update routerulebean set min_value=#{routeRuleBean.minValue},max_value=#{routeRuleBean.max},dstBankId=#{routeRuleBean.dstBankId} where routetype=#{routeRuleBean.routeType} and source_channel=#{routeRuleBean.sourceChannel}")
    int updateRouteRuleBean(@Param("routeRuleBean") RouteRuleBean routeRuleBean);


    @Insert("insert into routerulekeyvalue(routekey,routevalue,routetype) "
            + " VALUES(#{routeRuleKeyValue.routeKey},#{routeRuleKeyValue.routeValue},#{routeRuleKeyValue.routeType})")
    int addRouteRuleKeyValue(@Param("routeRuleKeyValue") RouteRuleKeyValue routeRuleKeyValue);
    @Insert("insert into routerulebean(source_channel,min_value,max_value,routetype,dstBankId) "
            + " VALUES(#{sourceChannel},#{minValue},#{max},#{routeType},#{dstBankId})")
    int addRouteRuleBean(RouteRuleBean routeRuleBean);
    @Select("select source_channel,min_value as minValue,max_value as max,routetype,dst_bank_name as dstBankName,dst_bank_ip as dstBankIp,dst_bank_port as dstBankPort from routerulebean rb,dstbank d where d.id=rb.dstBankId")
    @Results(id = "RouteRuleBeans", value = {
            @Result(property = "sourceChannel", column = "source_channel", javaType = String.class),
            @Result(property = "minValue", column = "min_value", javaType = String.class),
            @Result(property = "max", column = "max_value", javaType = String.class),
            @Result(property = "routeType", column = "routetype", javaType = String.class),
            @Result(property = "dstBankId", column = "dstBankId", javaType = String.class),
            @Result(property = "dstBankName", column = "dst_bank_name", javaType = String.class),
            @Result(property = "dstBankIp", column = "dst_bank_ip", javaType = String.class),
            @Result(property = "dstBankPort", column = "dst_bank_port", javaType = Integer.class),

    })
    List<RouteRuleBean> queryRouteRuleBeanList();

    @Select("select source_channel, min_value as minValue,max_value as max,routeType,dstBankId,dst_bank_name as dstBankName,dst_bank_ip as dstBankIp,dst_bank_port as dstBankPort from routerulebean rb,dstbank d where d.id=rb.dstBankId and routetype=#{routeType}")
    @Results(id = "RouteRuleBean", value = {
            @Result(property = "sourceChannel", column = "source_channel", javaType = String.class),
            @Result(property = "minValue", column = "min_value", javaType = String.class),
            @Result(property = "max", column = "max_value", javaType = String.class),
            @Result(property = "routeType", column = "routeType", javaType = String.class),
            @Result(property = "dstBankId", column = "dstBankId", javaType = String.class),
            @Result(property = "dstBankName", column = "dst_bank_name", javaType = String.class),
            @Result(property = "dstBankIp", column = "dst_bank_ip", javaType = String.class),
            @Result(property = "dstBankPort", column = "dst_bank_port", javaType = Integer.class),

    })
    List<RouteRuleBean> queryRouteRuleBeanListByType(String routeType);
    @Select("select routevalue from routerulekeyvalue where routekey=#{routeKey}")
    String queryRouteRuleValue(String routeKey);

    @Select("select routekey,routevalue,routetype from routerulekeyvalue where routetype=#{routeType}")
    @Results(id = "routeRuleKeyValue", value = { @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "routeKey", column = "routekey", javaType = String.class),
            @Result(property = "routeValue", column = "routevalue", javaType = String.class),
            @Result(property = "routeType", column = "routetype", javaType = String.class)
    })
    List<RouteRuleKeyValue> queryRouteRuleValueList(String routeType);
}
