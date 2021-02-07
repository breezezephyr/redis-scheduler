package com.sean.schedule;

import com.sean.schedule.annotation.EnableClusterScheduling;
import com.sean.schedule.redis.RedisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.concurrent.TimeUnit;

/**
 *
 * @Date 2019/3/26
 */
@SpringBootApplication
@EnableClusterScheduling
@Import(RedisConfig.class)
public class Run {
    public static void main(String[] args) throws InterruptedException {
//        args = new String[]{"--server.port=8080"};
        SpringApplication.run(Run.class, args);
        TimeUnit.HOURS.sleep(1);
    }
}
