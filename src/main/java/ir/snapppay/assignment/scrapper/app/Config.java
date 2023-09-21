package ir.snapppay.assignment.scrapper.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
@PropertySource(value = "classpath:config.properties")
public class Config {
}
