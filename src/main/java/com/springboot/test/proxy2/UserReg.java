package com.springboot.test.proxy2;

public class UserReg implements IWeb {
    @Override
    public void action() {
    System.out.println("我实现了用户注册的功能！");
    }
}
