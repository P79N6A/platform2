package com.springboot.service.pubsub;

import com.springboot.model.bean.Student;
import com.springboot.service.StudentService;
import com.springboot.util.SpringUtil;
import com.springboot.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPubSub;
@Service
public class MyListener extends JedisPubSub {
    Logger logger= LoggerFactory.getLogger(MyListener.class);
     @Override
    // 取得订阅的消息后的处理
    public void onMessage(String channel, String message) {
         logger.info("platform process MyListener:onMessage --begin :");
        System.out.println(channel + "=" + message);
        String result="";
        Object student= XmlUtil.xmlToBean2(message,Student.class);
         StudentService studentService = SpringUtil.getBean(StudentService.class);
            int i = studentService.add((Student) student);
            if (i == 1) {
                result = "platform add into db success!";
            } else {
                result = "platform add into db fail!";
            }
            System.out.print(result);
         logger.info("platform process MyListener:onMessage --end :"+result);
    }
     @Override
    // 初始化订阅时候的处理
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println(channel + "=" + subscribedChannels);
    }
    @Override
    // 取消订阅时候的处理
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println(channel + "=" + subscribedChannels);
    }
    @Override
    // 初始化按表达式的方式订阅时候的处理
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println(pattern + "=" + subscribedChannels);
    }
    @Override
    // 取消按表达式的方式订阅时候的处理
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        System.out.println(pattern + "=" + subscribedChannels);
    }

    @Override
    // 取得按表达式的方式订阅的消息后的处理
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println(pattern + "=" + channel + "=" + message);
    }
}

