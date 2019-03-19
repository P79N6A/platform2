package com.springboot.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheConfig;

import com.springboot.model.bean.Student;

import java.math.BigDecimal;

@Mapper
@CacheConfig(cacheNames = "student")
public interface StudentMapper {

	@Update("update student set sname=#{name},ssex=#{sex},age=#{age},idcard=#{idcard},phone=#{phone},bankno=#{bankno},area=#{area}," +
			"product=#{product},amount=#{amount}，remark=#{remark}")
	int update(Student student);

	@Delete("delete from student where sno=#{sno}")
	void deleteStudentBySno(String sno);

	@Insert("insert into student(sno,sname,ssex,age,idcard,phone,bankno,area,product,amount,remark) "
			+ " VALUES(#{sno},#{name},#{sex},#{age},#{idcard},#{phone},#{bankno},#{area},#{product},#{amount},#{remark})")
	int add(Student student);
	@Select("select * from student where sno=#{sno}")
	@Results(id = "student", value = {
			@Result(property = "sno", column = "sno", javaType = String.class),
			@Result(property = "name", column = "sname", javaType = String.class),
			@Result(property = "sex", column = "ssex", javaType = String.class)	,
			@Result(property = "age", column = "age", javaType = Integer.class),
			@Result(property = "idcard", column = "idcard", javaType = String.class),
			@Result(property = "phone", column = "phone", javaType = String.class),
			@Result(property = "bankno", column = "bankno", javaType = String.class),
			@Result(property = "area", column = "area", javaType = String.class),
			@Result(property = "amount", column = "amount", javaType = BigDecimal.class),
			@Result(property = "remark", column = "remark", javaType = String.class)})
	Student queryStudentBySno(String sno);
}
