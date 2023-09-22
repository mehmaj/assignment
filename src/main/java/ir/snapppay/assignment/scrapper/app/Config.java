package ir.snapppay.assignment.scrapper.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableMongoAuditing
@PropertySource(value = "classpath:config.properties")
@EnableAsync
@EnableScheduling
public class Config {
    @Value("${config.max.concurrency}")
    int MAX_CONCURRENCY;
    @Bean
    public Executor processExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(MAX_CONCURRENCY/10);
        executor.setMaxPoolSize(MAX_CONCURRENCY);
        executor.setQueueCapacity(MAX_CONCURRENCY/5);
        executor.setThreadNamePrefix("ProcessThread-");
        executor.initialize();
        return executor;
    }
}
