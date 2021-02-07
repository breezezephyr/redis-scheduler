package com.sean.schedule.config;

import com.sean.schedule.AbstractScheduler;
import com.sean.schedule.RedisSchedulerImpl;
import com.sean.schedule.ScheduledClusterAnnotationBeanPostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.TaskManagementConfigUtils;

/**
 *
 * @Date 2019/3/26
 */
@Slf4j
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class SchedulingClusterConfiguration {

    @Bean(name = TaskManagementConfigUtils.SCHEDULED_ANNOTATION_PROCESSOR_BEAN_NAME)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ScheduledAnnotationBeanPostProcessor scheduledAnnotationProcessor() {
        log.info("启用集群环境的定时任务");
        return new ScheduledClusterAnnotationBeanPostProcessor();
    }

    @Bean
    @ConditionalOnMissingBean(AbstractScheduler.class)
    public AbstractScheduler getScheduler() {
        return new RedisSchedulerImpl();
    }
}
