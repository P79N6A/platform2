package com.springboot;

import com.springboot.config.redis.RedisConfig;
import com.springboot.service.pubsub.SubThread;
import com.springboot.service.pubsub.Subscriber;
import com.springboot.service.socket.SocketServer;
import com.springboot.util.FileUtil;
import com.springboot.util.SpringUtil;
import com.springboot.util.socket.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@SpringBootApplication
@EnableCaching
@ComponentScan(value="com.springboot")
public class Application {
    //本机异步写入处理//订阅队列
	public static final String PLATFORM_CHANNEL_NAME_Processed = "PlatformProcessedQueue";
	public static final String PLATFORM_CHANNEL_NAME_Processing = "PlatformProcessingQueue";
	public static void main(String[] args) {
		Logger logger= LoggerFactory.getLogger(Application.class);
		SpringApplication.run(Application.class,args);
		/*logger.info("init Redis hot route");*/

		Server socketServer=new Server();
		socketServer.startServer();




	}
	public static void initRedis(String sourceConfigFile){
		FileUtil.toArrayByInputStreamReader(sourceConfigFile);
	}




}
