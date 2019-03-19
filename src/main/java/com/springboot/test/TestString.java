package com.springboot.test;

public class TestString {
    public static void main(String args[]){
        String str="random:MaShangFinTech:{XWBank:127.0.0.1:5001,HengShuiBank:127.0.0.1:5002,BaoTouNongShangBank:127.0.0.1:5003}";
    int index=str.indexOf("{");
    String subStr=str.substring(index+1,str.length()-1);
    System.out.println(subStr);
    String [] arg=subStr.split(":");
    System.out.println(arg);
    }

}
