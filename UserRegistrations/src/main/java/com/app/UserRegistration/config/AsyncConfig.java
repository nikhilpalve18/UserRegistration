package com.app.UserRegistration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public TaskExecutor getTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(4);
        threadPoolTaskExecutor.setMaxPoolSize(64);
        threadPoolTaskExecutor.setThreadNamePrefix("MyThread: ");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
