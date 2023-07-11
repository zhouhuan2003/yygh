package com.yygh.task.scheduled;/*
 *@author 周欢
 *@version 1.0
 */

import com.yygh.rabbit.constant.MqConst;
import com.yygh.rabbit.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduledTask {

    @Autowired
    private RabbitService rabbitService;
    /**
     * 每天8点执行 提醒就诊
     */
//    @Scheduled(cron = "0 0 8 * * ?")
    @Scheduled(cron = "0/30 * * * * ?")
    public void task1() {
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_TASK, MqConst.ROUTING_TASK_8, "");
    }
}
