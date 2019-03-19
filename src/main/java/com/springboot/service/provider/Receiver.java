package com.springboot.service.provider;


import com.springboot.model.bean.Student;
import com.springboot.model.route.SourceMsg;
import com.springboot.service.StudentService;
import com.springboot.service.platform.SourceMsgService;
import com.springboot.service.route.RouteService;
import com.springboot.util.JaXmlBeanUtil;
import com.springboot.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

public class Receiver {
    Logger logger=LoggerFactory.getLogger(Receiver.class);
    private CountDownLatch latch;
    @Autowired
    StudentService studentService;
    @Autowired
    private SourceMsgService sourceMsgService;
    public Receiver(CountDownLatch latch) {
        this.latch = latch;
    }

    public void receiveMessage(String message) {
        logger.info("in platform Receiver:receiveMessage---<" + message + ">");
        RouteService routeService = SpringUtil.getBean(RouteService.class);

        //本地转发处理
        String result = routeService.process(message);
        logger.info("in platform Receiver:receiveMessage--routeService.process-"+result);
        //异步写入
        SourceMsg sourceMsg = (SourceMsg) JaXmlBeanUtil.parseXmlToBean(SourceMsg.class,message);
        String studentxml = sourceMsg.getMsg();
        Student student=(Student) JaXmlBeanUtil.parseXmlToBean(Student.class,studentxml);
        int i = studentService.add(student);
        if (i == 1) {
            result = " platform add studentInfo into db success!";
        } else {
            result = "platform add studentInfo into db fail!";
        }
        logger.info("in platform Receiver:receiveMessage---"+result);


        logger.info("in platform Receiver:receiveMessage---"+result);
        latch.countDown();



    }
}

