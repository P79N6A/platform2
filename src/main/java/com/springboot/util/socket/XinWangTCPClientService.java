package com.springboot.util.socket;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

@Service
public class XinWangTCPClientService {
   /* @Autowired
    @Qualifier("destSocket")
    Socket dstsck;*/

    @Value("${xwdestSocketServer.port}")
    /**
     端口号
     */
    public  int port;
    @Value("${xwdestSocketServer.IP}")
    /**
     * 服务器端ip地址
     */
    public  String ip;
    public String transportOut(String msg) throws IOException{
        try {
            //创建客户端Socket，指定服务器地址和端口
            Socket socket = new Socket(ip, port);
            //建立连接后，获取输出流，向服务器端发送信息
            OutputStream os = socket.getOutputStream();
            //输出流包装为打印流
            PrintWriter pw = new PrintWriter(os);
            //向服务器端发送信息
            pw.write(msg);//写入内存缓冲区
            pw.flush();//刷新缓存，向服务器端输出信息
            socket.shutdownOutput();//关闭输出流

            //获取输入流，接收服务器端响应信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
            String data = null;


            //获取输入流，接收服务器端响应信息
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            data = null;
            while((data=br.readLine())!= null){
                System.out.println("我是场景平台客户端，新网银行服务器端返回信息为："+data);
            }

            //关闭其他资源
//            br.close();
//            is.close();
//            pw.close();
//            os.close();
            socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String resp="platform send XinWangBank ok through socket";
        return resp;
    }
}

