package com.springboot.model.bean;

import com.springboot.model.route.XmlElementAnno;
import lombok.Data;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@XmlRootElement(name = "student")
public class Student implements Serializable{

	private static final long serialVersionUID = -339516038496531943L;
	@XmlElement(name = "sno")
	@Column(name = "sno")
	@XmlElementAnno
	private String sno;

	@XmlElement(name = "name")
	@Column(name = "name")
	@XmlElementAnno
	private String name;

	@Column(name = "sex")
	@XmlElement(name = "sex")
	@XmlElementAnno
	private String sex;

	@Column(name = "age")
	@XmlElement(name = "age")
	@XmlElementAnno
	private int age;

	@Column(name = "idcard")
	@XmlElement(name = "idcard")
	@XmlElementAnno
	private String idcard;

	@Column(name = "phone")
	@XmlElement(name = "phone")
	@XmlElementAnno
	private String phone;

	@Column(name = "bankno")
	@XmlElement(name = "bankno")
	@XmlElementAnno
	private String bankno;


	@Column(name = "area")
	@XmlElement(name = "area")
	@XmlElementAnno
	private String area;


	@Column(name = "product")
	@XmlElement(name = "product")
	@XmlElementAnno
	private String product;

	@Column(name = "amount")
	@XmlElement(name = "amount")
	@XmlElementAnno
	private BigDecimal amount;

	@Column(name = "remark")
	@XmlElement(name = "remark")
	@XmlElementAnno
	private String remark;


	@XmlTransient
	public String getRemark() {
		return remark;
	}
	@XmlTransient
	public BigDecimal getAmount() {
		return amount;
	}
	@XmlTransient
	public String getProduct() {
		return product;
	}
	@XmlTransient
	public String getArea() {
		return area;
	}
	@XmlTransient
	public String getBankno() {
		return bankno;
	}
	@XmlTransient
	public String getPhone() {
		return phone;
	}
	@XmlTransient
	public String getIdcard() {
		return idcard;
	}
	@XmlTransient
	public int getAge() {
		return age;
	}


	@XmlTransient
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	@XmlTransient
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlTransient
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	
}
