/*
package com.springboot.util.socket;

import com.itextpdf.xmp.impl.Base64;
import rst.tcp.ftp.ContinueFTP2;
import rst.tcp.model.Student;
import rst.tcp.model.route.SourceMsg;
import rst.util.GsonUtil;
import rst.util.JaXmlBeanUtil;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Client {
    public static String sendXWContent(String date,String time){
    //传输内容

    Student student = new Student();
    student.setSno("008");
    student.setSex("male");
    student.setName("test");
    String studentXml = JaXmlBeanUtil.parseBeanToXml(Student.class, student);
    SourceMsg sourceMsg = new SourceMsg();
    sourceMsg.setChannelCode("XWBank");
    String seq="T20000012013090900000000"+new Random().nextInt(1000);
    String localfile1="d:/file/car2.jpg";
    String localfile2="d:/file/hamilton_clip.mp4";
    String localfile3="d:/file/car1.jpg";
    String localfile4="d:/file/orders.csv";
    List<String> localfiles=new ArrayList();
    localfiles.add(localfile1);
    localfiles.add(localfile2);
    localfiles.add(localfile3);
    localfiles.add(localfile4);
    String remotepath1="/信贷影像资料/归档/新网/"+date+"/"+seq+"/car2.jpg";
    String remotepath2="/信贷影像资料/渠道/新网/"+date+"/"+seq+"/hamilton_clip.mp4";
    String remotepath3="/信贷影像资料/渠道/新网/"+date+"/"+seq+"/car1.jpg";
    String remotepath4="/信贷影像资料/渠道/新网/"+date+"/"+seq+"/order.csv";
    List<String> remotepaths=new ArrayList<>();
    remotepaths.add(remotepath1);
    remotepaths.add(remotepath2);
    remotepaths.add(remotepath3);
    remotepaths.add(remotepath4);


    sourceMsg.setChannelSeq(seq);
    sourceMsg.setChannelDate(date);
    sourceMsg.setChannelTime(time);
    sourceMsg.setMsg(studentXml);
    String material= GsonUtil.toJSONString(sentByFtpClient(date,seq,localfiles,remotepaths));
    System.out.println(material);
    String b64=Base64.encode(material);
    sourceMsg.setMaterial(b64);
    sourceMsg.setCallbackUrl("http://localhost:8099/rst/callback");
    String content = JaXmlBeanUtil.parseBeanToXml(SourceMsg.class, sourceMsg);

    return content;


}

public static String sendMaShangFinTech(String date,String time){

    Student student = new Student();
    student.setSno("009");
    student.setSex("female");
    student.setName("zz");
    String studentXml = JaXmlBeanUtil.parseBeanToXml(Student.class, student);
    SourceMsg sourceMsg = new SourceMsg();
    sourceMsg.setChannelCode("MaShangFinTech");
    String seq="T20000012013090900000000"+new Random().nextInt(1000);
    String localfile1="d:/file/car1.jpg";
    String localfile2="d:/file/hamilton_clip.mp4";
    String localfile3="d:/file/car1.jpg";

    List<String> localfiles=new ArrayList();
    localfiles.add(localfile1);
    localfiles.add(localfile2);
    localfiles.add(localfile3);
    String remotepath1="/信贷影像资料/归档/马上金融/"+date+"/"+seq+"/car1.jpg";
    String remotepath2="/信贷影像资料/渠道/马上金融/"+date+"/"+seq+"/hamilton_clip.mp4";
    String remotepath3="/信贷影像资料/渠道/马上金融/"+date+"/"+seq+"/car1.jpg";
    List<String> remotepaths=new ArrayList<>();
    remotepaths.add(remotepath1);
    remotepaths.add(remotepath2);
    remotepaths.add(remotepath3);

    sourceMsg.setChannelSeq(seq);
    sourceMsg.setChannelDate(date);
    sourceMsg.setChannelTime(time);
    sourceMsg.setMsg(studentXml);

    String material= GsonUtil.toJSONString(sentByFtpClient(date,seq,localfiles,remotepaths));
    System.out.println(material);
    String b64=Base64.encode(material);
    sourceMsg.setMaterial(b64);
    */
/*sourceMsg.setMaterial(GsonUtil.toJSONString(remotepaths));*//*

    sourceMsg.setCallbackUrl("http://localhost:8099/rst/callback");
    String content = JaXmlBeanUtil.parseBeanToXml(SourceMsg.class, sourceMsg);
    return content;
}

    public static String sentByFtpClient(String date,String seq,List<String> localfiles,List<String> remotepaths){
        List<String> successList=new ArrayList<>();
        try {
            ContinueFTP2 myFtp = new ContinueFTP2("127.0.0.1", "ftp", "ftp", "21", "/信贷影像资料/归档/car1.jpg", "d:/car1.jpg");

            myFtp.connect(myFtp.ftpURL, new Integer(myFtp.ftpport), myFtp.username, myFtp.pwd);
            //myFtp.ftpClient.makeDirectory(new String("信贷影像资料".getBytes("GBK"), "iso-8859-1"));
            for(int i=0;i<localfiles.size();i++){
                myFtp.ftpClient.changeWorkingDirectory(new String("/".getBytes("GBK"), "iso-8859-1"));
                ContinueFTP2.UploadStatus result=myFtp.upload(localfiles.get(i), remotepaths.get(i));
                if (result==ContinueFTP2.UploadStatus.Upload_New_File_Success) {
                    successList.add(remotepaths.get(i));
                }
                System.out.println(result);
            }



            myFtp.disconnect();


        } catch (IOException e) {
            System.out.println("连接FTP出错：" + e.getMessage());
        }
        return GsonUtil.toJSONString(successList);
    }
    public static void main(String[] args) {
        SimpleDateFormat sdf=new SimpleDateFormat("YYYYmmdd");
        long now=System.currentTimeMillis();
        String date=sdf.format(now);
        SimpleDateFormat sdf2=new SimpleDateFormat("HHmmss");
        String time=sdf2.format(now);

        try {
            //创建客户端Socket，指定服务器地址和端口
            Socket socket = new Socket("localhost", 5000);
            //建立连接后，获取输出流，向服务器端发送信息
            OutputStream os = socket.getOutputStream();
            //输出流包装为打印流
            PrintWriter pw = new PrintWriter(os);
            //向服务器端发送信息
           // pw.write("用户名：zzh;密码：123");//写入内存缓冲区
            pw.write(sendXWContent(date,time));//写入内存缓冲区
            pw.flush();//刷新缓存，向服务器端输出信息
            socket.shutdownOutput();//关闭输出流

            //获取输入流，接收服务器端响应信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
            String data = null;
            while((data=br.readLine())!= null){
                System.out.println("我是新网客户端，服务器端提交信息为："+data);
            }
            socket = new Socket("localhost", 5000);
            //建立连接后，获取输出流，向服务器端发送信息
            os = socket.getOutputStream();
            pw.write(sendMaShangFinTech(date,time));//写入内存缓冲区
            pw.flush();//刷新缓存，向服务器端输出信息
            socket.shutdownOutput();//关闭输出流

            //获取输入流，接收服务器端响应信息
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            data = null;
            while((data=br.readLine())!= null){
                System.out.println("我是马上金融客户端，服务器端返回信息为："+data);
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
    }
}
*/
