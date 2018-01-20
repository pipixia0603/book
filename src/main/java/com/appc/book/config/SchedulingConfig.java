//package com.appc.book.config;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//
//@Configuration
//@EnableScheduling
//public class SchedulingConfig implements SchedulingConfigurer {
//    Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
//        scheduledTaskRegistrar.setTaskScheduler(taskScheduler());
//    }
//
//    @Bean(destroyMethod = "shutdown")
//    public ThreadPoolTaskScheduler taskScheduler() {
//        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
//        scheduler.setPoolSize(5);
//        scheduler.setThreadNamePrefix("dispatch-");
//        scheduler.setAwaitTerminationSeconds(600);
//        scheduler.setErrorHandler(throwable -> logger.error("调度任务发生异常", throwable));
//        scheduler.setWaitForTasksToCompleteOnShutdown(true);
//        return scheduler;
//    }
//
//}