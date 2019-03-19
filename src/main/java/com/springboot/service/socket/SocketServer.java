package com.springboot.service.socket;


/*import com.springboot.service.send.ACKTCPClientService;*/
import com.springboot.service.route.RouteService;
import com.springboot.util.JaXmlBeanUtil;
import com.springboot.util.SpringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * nio socket服务端
 */
@Service
public class SocketServer {
    /*@Autowired
    ACKTCPClientService acktcpClientService;*/
    // 日志
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);
    /**
     *  放入平台正在处理中队列
     */
    @Value("${PLATFORM_CHANNEL_NAME_Processing}")
    String PLATFORM_CHANNEL_NAME_Processing;
    /**
     * 启动socket服务，开启监听
     * @param port
     * @throws IOException
     */
    public void startSocketServer(int port){
        try {
            //1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
            ServerSocket serverSocket=new ServerSocket(port);
            Socket socket=null;
            //记录客户端的数量
            int count=0;
            System.out.println("***服务器即将启动，等待客户端的连接***");
            //循环监听等待客户端的连接
            while(true){
                //调用accept()方法开始监听，等待客户端的连接
                socket=serverSocket.accept();
                InputStream is=null;
                OutputStream os=null;
                try {
                    StringBuffer reqSb = new StringBuffer();
                    //获取输入流，并读取客户端信息
                    is = socket.getInputStream();
                    byte[] buff = new byte[1024];
                    byte[] all = new byte[0];
                    int len = 0;
                    while((len = is.read(buff)) != -1){
                        all = ArrayUtils.addAll(all,ArrayUtils.subarray(buff,0,len));
                    }
                    //socket.shutdownInput();//关闭输入流
                    String reqxml = new String(all, "gbk");
                    RouteService routeService = SpringUtil.getBean(RouteService.class);

                    //本地转发处理
                    String result=routeService.process(reqxml);
                    //放入本机消息队列进行异步写入，沉淀数据
                     if(reqxml.contains("<sourcemsg>")) {
                         System.out.println("将收到的源消息放入平台正在处理中队列");
                         StringRedisTemplate template = SpringUtil.getBean(StringRedisTemplate.class);
                         CountDownLatch latch = SpringUtil.getBean(CountDownLatch.class);
                         template.convertAndSend(PLATFORM_CHANNEL_NAME_Processing, reqxml);
                         try {
                             //发送消息连接等待中
                             System.out.println("消息放入平台正在处理中队列正在发送...");

                             latch.await();
                         } catch (InterruptedException e) {
                             System.out.println("消息放入平台正在处理中队列发送失败...");
                         }
                     }
                  /*   if(reqxml.contains("<ackMsg>")){
                         LOGGER.info("bank process result in thirdPlatform:"+reqxml);
                         try {

                             acktcpClientService.transportOut(reqxml);
                         }catch (Exception e){
                             LOGGER.error("银行处理完毕后回复场景平台出错，出错信息："+e.getMessage());
                         }
                     }*/
                    result="platform has received info.";

                    if(!result.equals("")) {
                        byte[] bstream = result.getBytes("GBK");  //转化为字节流
                        os = socket.getOutputStream();   //输出流
                        os.write(bstream);
                        os.flush();//调用flush()方法将缓冲输出

                        LOGGER.info("from platform return platform info");
                    }else{
                        LOGGER.info("from bank return blank info");
                    }
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }finally{
                    //关闭资源
                   try {
                        if(os!=null) {
                            os.close();
                        }
                        if(is!=null) {
                            is.close();
                        }

                   } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

