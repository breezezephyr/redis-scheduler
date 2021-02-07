package com.sean.schedule.annotation;

import com.sean.schedule.config.SchedulingClusterConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用集群环境的定时任务
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SchedulingClusterConfiguration.class)
@Documented
public @interface EnableClusterScheduling {

}
