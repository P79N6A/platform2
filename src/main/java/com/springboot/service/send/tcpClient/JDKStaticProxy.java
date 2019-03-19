package com.springboot.service.send.tcpClient;

import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;

public class JDKStaticProxy implements TCPClientService {
    private TCPClientService target;  //组合一个业务实现类对象来进行真正的业务方法的调用
    /**
     端口号
     */
    public  int port;

    /**
     * 服务器端ip地址
     */
    public  String ip;
    /**
     * 覆盖默认构造器
     *
     * @param target
     */
    public JDKStaticProxy(TCPClientService target) {
        this.target = target;
    }

    @Override
    public String transportOut(String msg) throws IOException {
        System.out.println("发送的预处理——————");
        // 调用真正的查询账户方法
        String resp=target.transportOut(msg);
        System.out.println("发送之后————————");
        return resp;
    }


}
