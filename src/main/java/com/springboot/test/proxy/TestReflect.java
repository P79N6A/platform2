package com.springboot.test.proxy;

import com.springboot.test.proxy.F;

public class TestReflect {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
            F f = new F();
            // 第一种表达方式
            Class c1 = F.class;// 这种表达方式同时也告诉了我们任何一个类都有一个隐含的静态成员变量class
            // 第二种表达方式
            Class c2 = f.getClass();// 这种表达方式在已知了该类的对象的情况下通过getClass方法获取
            // 第三种表达方式
            Class c3 = Class.forName("com.springboot.test.proxy.F");// 类的全称

            System.out.println(c1.getName());
            System.out.println(c2.getName());
            System.out.println(c3.getName());
            F temp = (F) c1.newInstance();//这里可以是c1/c2/c3
            System.out.println(temp.f);
            temp.save();
        }
    }


