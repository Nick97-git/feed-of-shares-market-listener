package com.archangel.feedofsharesmarketlistener.producer;

import static com.archangel.feedofsharesmarketlistener.config.RabbitMqConfig.SUPER_STREAM_NAME;
import static com.archangel.feedofsharesmarketlistener.config.RabbitMqConfig.SUPER_STREAM_PARTITIONS_NUMBER;

import com.archangel.feedofsharesmarketlistener.dto.MarketEvent;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@AllArgsConstructor
@Component
public class MarketEventProducer {
  private static final String UNIQUE_KEY_FORMAT = "%s_%s";

  private final RabbitTemplate rabbitTemplate;

  @SneakyThrows
  public void sendEvent(MarketEvent event) {
    log.info("Send event = {}", event);
    String routingKey = createRoutingKey(event);
    rabbitTemplate.convertAndSend(SUPER_STREAM_NAME, routingKey, event);
  }

  private String createRoutingKey(MarketEvent event) {
    String uniqueKey = UNIQUE_KEY_FORMAT.formatted(event.username(), event.ticker());
    long routingKey = Math.abs(uniqueKey.hashCode()) % SUPER_STREAM_PARTITIONS_NUMBER;
    return String.valueOf(routingKey);
  }
}
