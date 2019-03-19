package com.springboot.model.route;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@XmlRootElement(name = "routemsg")
public class RouteMsg implements Serializable {
    @XmlElement(name = "channelDate")
    @Column(name = "channelDate")
    @XmlElementAnno
    /**
     * 渠道交易日期,格式为 YYYYMMDD

     */
    String channelDate;
    @XmlElement(name = "creater")
    @Column(name = "creater")
    @XmlElementAnno
    /**
     * 处理者

     */
    String creater;

    /**
     * 格式为 HHMMSSNNN
     */

    @Column(name = "channelTime")
    @XmlElement(name = "channelTime")
    @XmlElementAnno
    String channelTime;
    /**
     *  渠道流水号,表示渠道的唯一流水,前缀（T）+源系统标识号（7 位）+源系统交易日期（8 位：YYYYMMDD）+交易流水序号（12 位）
     */

    @XmlElement(name = "channelSeq")
    @Column(name = "channelSeq")
    @XmlElementAnno
    String channelSeq;

    @XmlElement(name = "material")
    @Column(name = "material")
    @XmlElementAnno
    String material;
    /**
     * 渠道系统标识:合作方系统标识
     */

    @XmlElement(name = "channelCode")
    @Column(name = "channelCode")
    @XmlElementAnno
    String channelCode;

    /**
     * 平台标识
     */

    @XmlElement(name = "platformCode")
    @Column(name = "platformCode")
    @XmlElementAnno
    String platformCode;

    /**
     * 资金方标识
     */

    @XmlElement(name = "destBankCode")
    @Column(name = "destBankCode")
    @XmlElementAnno
    String destBankCode;

    @XmlElement(name = "msg")
    @Column(name = "msg")
    @XmlElementAnno
    String msg;


    @XmlElement(name = "callbackUrl")
    @Column(name = "callbackUrl")
    @XmlElementAnno
    String callbackUrl;

    @XmlElement(name = "result")
    @Column(name = "result")
    @XmlElementAnno
    String result;

    @XmlTransient
    public String getCreater() {
        return creater;
    }
    @XmlTransient
    public String getCallbackUrl() {
        return callbackUrl;
    }
    @XmlTransient
    public String getMsg() {
        return msg;
    }
    @XmlTransient
    public String getResult() {
        return result;
    }
    @XmlTransient
    public String getMaterial() {
        return material;
    }
    @XmlTransient
    public String getChannelCode() {
        return channelCode;
    }
    @XmlTransient
    public String getChannelDate(){
        return channelDate;
    }
    @XmlTransient
    public String getChannelTime() {
        return channelTime;
    }
    @XmlTransient
    public String getChannelSeq() {
        return channelSeq;
    }
    @XmlTransient
    public String getDestBankCode() {
        return destBankCode;
    }
    @XmlTransient
    public String getPlatformCode() {
        return platformCode;
    }

    public void setChannelDate(String channelDate){
        this.channelDate=channelDate;
    }
    public void setChannelTime(String channelTime){
        this.channelTime=channelTime;
    }
    public void setChannelSeq(String channelSeq){
        this.channelSeq=channelSeq;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public void setDestBankCode(String destBankCode) {
        this.destBankCode = destBankCode;
    }
    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }
    public void setMaterial(String material) {
        this.material = material;
    }
    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public void setCreater(String creater) {
        this.creater = creater;
    }
}
