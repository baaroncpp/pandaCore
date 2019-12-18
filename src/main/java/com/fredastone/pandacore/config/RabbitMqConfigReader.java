package com.fredastone.pandacore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import lombok.Getter;

@Configuration
@PropertySource("classpath:application.properties")
public class RabbitMqConfigReader {

@Getter	
@Value("${notification.exchange.name}")
private String notificationExchange;

@Getter
@Value("${notification.queue.email.name}")
private String emailQueue;

@Getter
@Value("${notification.queue.sms.name}")
private String smsQueue;

@Getter
@Value("${notification.routing.sms.key}")
private String smsRoutingKey;

@Getter
@Value("${notification.routing.email.key}")
private String emailRoutingKey;

}