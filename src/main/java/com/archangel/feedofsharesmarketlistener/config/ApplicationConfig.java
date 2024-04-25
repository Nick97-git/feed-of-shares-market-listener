package com.archangel.feedofsharesmarketlistener.config;

import com.archangel.feedofsharesmarketlistener.consumer.processor.MarketEventProcessor;
import com.archangel.feedofsharesmarketlistener.dto.MarketEvent;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class ApplicationConfig {
  private final List<MarketEventProcessor> processors;

  @Bean
  public Map<MarketEvent.Operation, MarketEventProcessor> marketEventProcessorByOperationMap() {
    return processors.stream()
        .collect(Collectors.toMap(MarketEventProcessor::getOperation, Function.identity()));
  }
}
