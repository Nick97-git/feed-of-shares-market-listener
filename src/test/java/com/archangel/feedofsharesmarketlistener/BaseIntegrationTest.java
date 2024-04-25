package com.archangel.feedofsharesmarketlistener;

import com.rabbitmq.stream.Environment;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.rabbit.stream.listener.StreamListenerContainer;

@SpringBootTest(
    properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration",
        "spring.main.allow-bean-definition-overriding=true"
    }
)
public class BaseIntegrationTest {
  @MockBean
  private ConnectionFactory connectionFactory;
  @MockBean
  private RabbitTemplate rabbitTemplate;
  @MockBean
  private RabbitAdmin rabbitAdmin;
  @MockBean
  private Environment environment;
  @MockBean
  private StreamListenerContainer streamListenerContainer;
}
