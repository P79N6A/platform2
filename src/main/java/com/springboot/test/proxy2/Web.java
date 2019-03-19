package com.springboot.test.proxy2;

import com.springboot.test.proxy2.IWeb;

/*
 * web功能实现类
 * 动态加载举例
 */
public class Web {

    public static void main(String[] args) {
        try {
            Class<?> w = Class.forName("com.springboot.test.proxy2." + args[0]);
            IWeb i = (IWeb) w.newInstance();
            i.action();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
