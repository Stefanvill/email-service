package se.iths.stefan.emailservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE = "email-queue";

    @Bean
    public Queue emailQueue() {
        return new Queue(QUEUE);
    }
}