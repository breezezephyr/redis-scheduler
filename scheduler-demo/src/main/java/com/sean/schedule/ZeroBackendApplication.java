package com.sean.schedule;

import com.sean.schedule.annotation.EnableClusterScheduling;
import com.sean.schedule.redis.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@Slf4j
@MapperScan({"com.zero.backend.mapper"})
@SpringBootApplication
@Import({
        RedisConfig.class
})

@EnableClusterScheduling
public class ZeroBackendApplication implements ApplicationRunner {

    @Value("${service.name}")
    private String serviceName;


    @Value("${spring.profiles:local}")
    private String env;



    public static void main(String[] args) {
        SpringApplication.run(ZeroBackendApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments applicationArguments) {
        log.info("{}-{} coming!", serviceName, env);
    }

}
