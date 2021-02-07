package com.sean.schedule.task;

import com.sean.schedule.annotation.ScheduledCluster;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 工具任务
 */
@Slf4j(topic = "SeanTask")
@Configuration
public class ToolsTask {

    /**
     * 结束任务
     */
    @ScheduledCluster("end_task")
    @Scheduled(fixedDelay = 5000)
    public void endCourseHourTask() {
        log.info("结束课时开始.");
        log.info("结束课时结束.");
    }

    /**
     * 场景恢复
     */
    @ScheduledCluster("restore_task")
    @Scheduled(cron = "0/3 * * * * ?")
    public void restoreAgentTask() {
        log.info("scan agent pid task");
        log.info("end scan agent pid task");
    }

    /**
     * 更新比赛
     */
    @ScheduledCluster("update_task")
    @Scheduled(cron = "0 */1 * * * ?")
    public void updateGameGroupParam() {
        log.info("start update param");
        log.info("end update param");
    }

}
