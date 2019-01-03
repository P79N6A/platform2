package com.springboot.mapper;

import com.springboot.model.route.RouteMsg;
import com.springboot.model.route.SourceMsg;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheConfig;

@Mapper
@CacheConfig(cacheNames = "routeMsg")
public interface RouteMsgMapper {


    @Delete("delete from routemsgbean where id=#{id}")
    void deleteRouteMsgById(int id);

   /* @Insert("insert into sourcemsg(msg) "
            + " VALUES(#{msg})")
    int addSourceMsgString(String sourceMsg);*/
    @Insert("insert into routemsgbean(destBankCode,platformCode,channelDate,channelTime,channelSeq,channelCode,msg) "
            + " VALUES(#{destBankCode},#{platformCode},#{channelDate},#{channelTime},#{channelSeq},#{channelCode},#{msg})")
    int add(RouteMsg rMsg);
    @Select("select * from routemsgbean where id=#{id}")
    @Results(id = "routemsg", value = { @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "channelDate", column = "channelDate", javaType = String.class),
            @Result(property = "channelTime", column = "channelTime", javaType = String.class),
            @Result(property = "channelSeq", column = "channelSeq", javaType = String.class),
            @Result(property = "channelCode", column = "channelCode", javaType = String.class),
            @Result(property = "msg", column = "msg", javaType = String.class),
            @Result(property = "destBankCode", column = "destBankCode", javaType = String.class),
            @Result(property = "platformCode", column = "platformCode", javaType = String.class),
    })
    SourceMsg querySourceMsgById(int id);
    @Select("select * from routemsg where id=#{id}")
    @Results(id = "routemsgString", value = { @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "msg", column = "msg", javaType = String.class)})
    String querySourceMsgStringById(int id);
}
