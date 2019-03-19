package com.springboot.model.route;

public enum BankEnum {
    新网银行(1, "XWBank"),
    衡水银行(2, "HengShuiBank"),
    包头农商行(3,"BaoTouNongShangBank"),
    北京银行(4, "BeiJingBank"),
    ElseBank(5,"elseBank");
    // 成员变量
     private int id;
     private String bankName;
     // 构造方法
     private BankEnum(int id, String bankName) {
         this.id = id;
         this.bankName = bankName;
     }
    //普通方法
 public static int getId(String bankName)
 {
     for (BankEnum c : BankEnum.values()) {
     if (c.getBankName() == bankName) {
         return c.id;
     }
 }
    return 0;
 }
 // get set 方法
 public String getBankName() {
         return bankName;
     }
 public void setBankName(String bankName) {
         this.bankName = bankName;
     }
 public int getId() {
     return id;
 }
 public void setId(int id) {
     this.id = id;
 }
}
