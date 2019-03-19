package com.springboot.model.bean;

public enum Channel {
    M("MaShangFinTech", "2000007"),
    XW("XW", "2000008"),
    ChangAn("ChangAn","2000009"),
    QiangWei("QiangWei", "2000010"),
    ElseChannel("...","2000011");
    // 成员变量
     private String name;
     private String channelCode;
     // 构造方法
     private Channel(String name, String channelCode) {
         this.name = name;
         this.channelCode = channelCode;
     }
    //普通方法
 public static String getName(String channelCode)
 {
     for (Channel c : Channel.values()) {
     if (c.getChannelCode() == channelCode) {
         return c.name;
     }
 }
    return null;
 }
 // get set 方法
 public String getName() {
         return name;
     }
 public void setName(String name) {
         this.name = name;
     }
 public String getChannelCode() {
     return channelCode;
 }
 public void setIndex(String channelCode) {
     this.channelCode = channelCode;
 }
}
