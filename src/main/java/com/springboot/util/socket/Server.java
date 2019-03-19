package com.springboot.util.socket;

import com.springboot.service.route.RouteService;
import com.springboot.util.SpringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class Server {


    public  void startServer() {
        try {
            //创建一个服务器端的Socket，即ServerSocket,绑定需要监听的端口
            ServerSocket serverSocket = new ServerSocket(5000);
            Socket socket = null;
            //记录连接过服务器的客户端数量
            int count = 0;
            System.out.println("***服务器即将启动，等待客户端的连接***");
            while (true) {//循环侦听新的客户端的连接
                //调用accept（）方法侦听，等待客户端的连接以获取Socket实例
                socket = serverSocket.accept();
                //创建新线程
                Thread thread = new Thread(new ServerThread(socket));
                thread.start();

                count++;
                System.out.println("服务器端被连接过的次数：" + count);
                InetAddress address = socket.getInetAddress();
                System.out.println("当前客户端的IP为：" + address.getHostAddress());
                 //serverSocket.close();一直循环监听，不用关闭连接
                }
            }catch (IOException e) {
            e.printStackTrace();
        }
        }
    public static void main(String[] args) {
        try {
            //创建一个服务器端的Socket，即ServerSocket,绑定需要监听的端口
            ServerSocket serverSocket = new ServerSocket(5000);
            Socket socket = null;
            //记录连接过服务器的客户端数量
            int count = 0;
            System.out.println("***服务器即将启动，等待客户端的连接***");
            while(true){//循环侦听新的客户端的连接
                //调用accept（）方法侦听，等待客户端的连接以获取Socket实例
                socket = serverSocket.accept();
                //创建新线程
                Thread thread = new Thread(new ServerThread(socket));
                thread.start();

                count++;
                System.out.println("服务器端被连接过的次数："+count);
                InetAddress address = socket.getInetAddress();
                System.out.println("当前客户端的IP为："+address.getHostAddress());
            }
            //serverSocket.close();一直循环监听，不用关闭连接
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
