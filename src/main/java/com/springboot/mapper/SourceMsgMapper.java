package com.springboot.mapper;

import com.springboot.model.route.SourceMsg;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheConfig;

@Mapper
@CacheConfig(cacheNames = "sourceMsg")
public interface SourceMsgMapper {


    @Delete("delete from sourcemsg where id=#{id}")
    void deleteSourceMsgById(int id);
    @Update("update sourcemsg set result=#{result} where id=#{id}")
    void updateSourceMsgById(int id,String result);

    @Update("update sourcemsgbean set result=#{result} where id=#{id}")
    void updateSourceMsgBeanById(int id,String result);

    @Insert("insert into sourcemsg(msg,material,result) "
            + " VALUES(#{msg},#{material},#{result})")
    int addSourceMsgString(String sourceMsg);
    @Insert("insert into sourcemsgbean(channelDate,channelTime,channelSeq,channelCode,msg,material,result) "
            + " VALUES(#{channelDate},#{channelTime},#{channelSeq},#{channelCode},#{msg},#{material},#{result})")
    int add(SourceMsg sourceMsg);
    @Select("select * from sourcemsgbean where channelSeq=#{channelSeq}")
    @Results(id = "sourcemsgbean", value = { @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "channelDate", column = "channelDate", javaType = String.class),
            @Result(property = "channelTime", column = "channelTime", javaType = String.class),
            @Result(property = "channelSeq", column = "channelSeq", javaType = String.class),
            @Result(property = "channelCode", column = "channelCode", javaType = String.class),
            @Result(property = "msg", column = "msg", javaType = String.class),
            @Result(property = "material", column = "material", javaType = String.class),
            @Result(property = "result", column = "result", javaType = String.class)
    })
    SourceMsg querySourceMsgById(String  channelSeq);
    @Select("select * from sourcemsg where id=#{id}")
    @Results(id = "sourcemsg", value = { @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "msg", column = "msg", javaType = String.class),
            @Result(property = "material", column = "material", javaType = String.class),
            @Result(property = "result", column = "result", javaType = String.class)
    })
    SourceMsg querySourceMsgStringById(int id);
}
