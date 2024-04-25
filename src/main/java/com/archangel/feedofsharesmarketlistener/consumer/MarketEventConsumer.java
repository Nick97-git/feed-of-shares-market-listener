package com.archangel.feedofsharesmarketlistener.consumer;

import com.archangel.feedofsharesmarketlistener.consumer.processor.MarketEventProcessorStrategy;
import com.archangel.feedofsharesmarketlistener.dto.MarketEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MarketEventConsumer implements MessageListener {
  private final MarketEventProcessorStrategy processorStrategy;
  private final ObjectMapper objectMapper;

  @SneakyThrows
  @Override
  public void onMessage(Message message) {
    MarketEvent marketEvent = objectMapper.readValue(message.getBody(), MarketEvent.class);
    processorStrategy.process(marketEvent);
  }
}
