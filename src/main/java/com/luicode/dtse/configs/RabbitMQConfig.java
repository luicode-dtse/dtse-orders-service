package com.luicode.dtse.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange("order.exchange");
    }

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue("order.created.queue", true);
    }

    @Bean
    public Binding orderCreatedBinding() {
        return BindingBuilder.bind(orderCreatedQueue())
                .to(orderExchange())
                .with("order.created");
    }

    @Bean
    public Queue orderCompletedQueue() {
        return new Queue("order.completed.queue", true);
    }

    @Bean
    public Queue orderFailedQueue() {
        return new Queue("order.failed.queue", true);
    }

    @Bean
    public Binding orderCompletedBinding() {
        return BindingBuilder.bind(orderCompletedQueue())
                .to(orderExchange())
                .with("order.completed");
    }

    @Bean
    public Binding orderFailedBinding() {
        return BindingBuilder.bind(orderFailedQueue())
                .to(orderExchange())
                .with("order.failed");
    }

}

