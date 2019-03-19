package com.springboot.test.proxy2;

public class SendEmail implements IWeb {
    @Override
    public void action() {
        System.out.println("我实现了发送电子邮件的功能");
        }
 }
