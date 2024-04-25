package com.archangel.feedofsharesmarketlistener.config;

import com.archangel.feedofsharesmarketlistener.consumer.MarketEventConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.rabbit.stream.config.SuperStream;
import org.springframework.rabbit.stream.listener.StreamListenerContainer;

@AllArgsConstructor
@Configuration
public class RabbitMqConfig {
  public static final String SUPER_STREAM_NAME = "s.super.market.event";
  private static final String SUPER_STREAM_CONSUMER_NAME = "super-stream-consumer";
  public static final int SUPER_STREAM_PARTITIONS_NUMBER = 3;

  private final MarketEventConsumer marketEventConsumer;

  @Bean
  RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
    RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
    rabbitAdmin.declareQueue();
    rabbitAdmin.setAutoStartup(true);
    return rabbitAdmin;
  }

  @Bean
  SuperStream superStreamNumber() {
    return new SuperStream(SUPER_STREAM_NAME, SUPER_STREAM_PARTITIONS_NUMBER);
  }

  @Bean
  public RabbitTemplate rabbitTemplate(
      ConnectionFactory connectionFactory,
      Jackson2JsonMessageConverter jsonMessageConverter
  ) {
    RabbitTemplate template = new RabbitTemplate();
    template.setConnectionFactory(connectionFactory);
    template.setMessageConverter(jsonMessageConverter);
    return template;
  }

  @Bean
  public Jackson2JsonMessageConverter jsonMessageConverter(@Autowired ObjectMapper objectMapper) {
    return new Jackson2JsonMessageConverter(objectMapper);
  }

  @Bean(name = "superStreamMarketEventContainer")
  public StreamListenerContainer superStreamMarketEventContainer(Environment environment) {
    StreamListenerContainer container = new StreamListenerContainer(environment);
    container.superStream(SUPER_STREAM_NAME, SUPER_STREAM_CONSUMER_NAME, 1);
    container.setupMessageListener(marketEventConsumer);
    container.setConsumerCustomizer((id, builder) -> builder.offset(OffsetSpecification.last()));
    return container;
  }
}
