package com.springboot.service.pubsub;

import com.springboot.model.bean.Student;
import com.springboot.model.route.SourceMsg;
import com.springboot.service.StudentService;
import com.springboot.service.platform.SourceMsgService;
import com.springboot.util.GsonUtil;
import com.springboot.util.JaXmlBeanUtil;
import com.springboot.util.RestClient;
import com.springboot.util.SpringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.JedisPubSub;

public class Subscriber extends JedisPubSub {//注意这里继承了抽象类JedisPubSub

    private static final Logger LOGGER = Logger.getLogger(Subscriber.class);
    @Autowired
    StudentService studentService;
    @Autowired
    private SourceMsgService sourceMsgService;
    @Value("${channelCallbackUrl}")
    private String channelCallbackUrl;
    @Override
    public void onMessage(String channel, String message) {
        LOGGER.info("in platform Subscriber:onMessage---");
        LOGGER.info(String.format("platform Message. Channel: %s, Msg: %s", channel, message));
        System.out.println("platform Received <" + message + ">");

        //异步写入
        //StudentService studentService = SpringUtil.getBean(StudentService.class);
        SourceMsg sourceMsg = (SourceMsg) JaXmlBeanUtil.parseXmlToBean(SourceMsg.class,message);
        String studentxml = sourceMsg.getMsg();
        Student student=(Student) JaXmlBeanUtil.parseXmlToBean(Student.class,studentxml);
        int i = studentService.add(student);
        i=sourceMsgService.add(sourceMsg);

        String result;
        if (i == 1) {
            result = " platform add into db success!";
        } else {
            result = "platform fail!";
        }
        LOGGER.info("in platform Subscriber:onMessage---"+result);
       //回调platform
        sourceMsg.setResult("success");
        RestClient.restTemplate(channelCallbackUrl, GsonUtil.toJSONString(sourceMsg));

    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        LOGGER.info(String.format("platform  PMessage. Pattern: %s, Channel: %s, Msg: %s",
                pattern, channel, message));
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {

        LOGGER.info("platform  onSubscribe");
        LOGGER.info("in platform Subscriber:onSubscribe---");


    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {

        LOGGER.info("platform  onUnsubscribe");

    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        LOGGER.info("platform  onPUnsubscribe");
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        LOGGER.info("platform  onPSubscribe");
    }
}
