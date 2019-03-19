package com.springboot.util.socket;

import com.springboot.util.SpringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class ServerThread implements Runnable{
    Logger logger= LoggerFactory.getLogger(ServerThread.class);
    /**
     * 和本线程相关的Socket
     */
    Socket socket = null;

    /**
     * 放入平台正在处理中队列
     */

    public static final String PLATFORM_CHANNEL_NAME_Processing = "PlatformProcessingQueue";
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;
        try {
            //与客户端建立通信，获取输入流，读取取客户端提供的信息
            is = socket.getInputStream();
            /*isr = new InputStreamReader(is,"GBK");
            br = new BufferedReader(isr);
            String data = null;
            while((data=br.readLine()) != null){//循环读取客户端的信息
                System.out.println("我是服务器，客户端提交信息为："+data);
            }
            socket.shutdownInput();//关闭输入流*/

            //获取输入流，并读取客户端信息
            is = socket.getInputStream();
            byte[] buff = new byte[1024];
            byte[] all = new byte[0];
            int len = 0;
            while ((len = is.read(buff)) != -1) {
                all = ArrayUtils.addAll(all, ArrayUtils.subarray(buff, 0, len));
            }
            socket.shutdownInput();//关闭输入流
            String reqxml = new String(all, "gbk");
            if(reqxml.contains("XWBank")){
                logger.info("我是场景平台服务器，新网客户端提交信息为："+reqxml);
            }
            if(reqxml.contains("MaShangFinTech")){
                logger.info("我是场景平台服务器，马上消费客户端提交信息为："+reqxml);
            }


            //获取输出流，响应客户端的请求
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.write("场景平台服务器端收到消息成功！");
            pw.flush();




            //放入本机消息队列进行异步写入，沉淀数据

                System.out.println("将收到的源消息放入平台正在处理中队列");
                StringRedisTemplate template = SpringUtil.getBean(StringRedisTemplate.class);
                CountDownLatch latch = SpringUtil.getBean(CountDownLatch.class);
                template.convertAndSend(PLATFORM_CHANNEL_NAME_Processing, reqxml);
                try {
                    //发送消息连接等待中
                    logger.info("消息放入平台正在处理中队列正在发送...");
                    latch.await();
                } catch (InterruptedException e) {
                    logger.info("消息放入平台正在处理中队列发送失败...");
                }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭资源即相关socket
            try {
                if(pw!=null) {
                    pw.close();
                }
                if(os!=null) {
                    os.close();
                }
                if(br!=null) {
                    br.close();
                }
                if(isr!=null) {
                    isr.close();
                }
                if(is!=null) {
                    is.close();
                }
                if(socket!=null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
