package com.example.basic.timedtask;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail teatQuartzDetail(){

        return JobBuilder.newJob(TestQuartz.class).withIdentity("testQuartz").storeDurably().build();

    }

//    @Bean
//    public Trigger testQuartzTrigger(){
//
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInSeconds(10)  //设置时间周期单位秒
//                .repeatForever();
//        return (Trigger) TriggerBuilder.newTrigger().forJob(teatQuartzDetail())
//
//                .withIdentity("testQuartz")
//                .withSchedule(scheduleBuilder)
//                .build();
//
//    }

}