package com.mawenxia.mail.config.scheduler;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by sam on 2017/6/4.
 * <p>
 * coding like an artist
 */
@Configuration
@EnableScheduling
public class TaskSchedulerConfig implements SchedulingConfigurer{

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskSchduler());
    }

    @Bean(destroyMethod = "shutdown")
    public Executor taskSchduler(){
        return Executors.newScheduledThreadPool(100);
    }
}
