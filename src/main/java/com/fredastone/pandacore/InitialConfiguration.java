package com.fredastone.pandacore;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fredastone.pandacore.config.RabbitMqConfigReader;


@Configuration
@ComponentScan(basePackages= {"com.fredastone"})
@EnableJpaRepositories(basePackages= {"com.fredastone"})
@EnableTransactionManagement
public class InitialConfiguration {
	
	/* This bean is to read the properties file configs */
	@Autowired
	private RabbitMqConfigReader applicationConfig;

	public RabbitMqConfigReader getApplicationConfig() {

		return applicationConfig;

	}

	public void setApplicationConfig(RabbitMqConfigReader applicationConfig) {	
		this.applicationConfig = applicationConfig;

	}
	

	@Bean
	public RabbitMqConfigReader applicationConfig() {

		return new RabbitMqConfigReader();

	}
	/* Creating a bean for the Message queue Exchange */

	@Bean

	public TopicExchange getNotificationExchange() {

	return new TopicExchange(getApplicationConfig().getNotificationExchange());

	}

	/* Creating a bean for the Message queue */

	@Bean
	public Queue getSmsQueue() {

	return new Queue(getApplicationConfig().getSmsQueue(),Boolean.TRUE,Boolean.FALSE,Boolean.FALSE);

	}
	
	
	@Bean
	public Queue getEmailQueue() {

	return new Queue(getApplicationConfig().getEmailQueue(),Boolean.TRUE,Boolean.FALSE,Boolean.FALSE);

	}
	/* Binding between Exchange and Queue using routing key */

	@Bean
	public Binding declareBindingEmail() {

		return BindingBuilder.bind(getEmailQueue()).to(getNotificationExchange()).with(getApplicationConfig().getEmailRoutingKey());

	}
	
	@Bean
	public Binding declareBindingSMS() {

		return BindingBuilder.bind(getSmsQueue()).to(getNotificationExchange()).with(getApplicationConfig().getSmsRoutingKey());

	}

	/* Bean for rabbitTemplate */

	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {

	final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		return rabbitTemplate;

	}
	
	
	
}
