package com.archangel.feedofsharesmarketlistener.consumer.processor;

import com.archangel.feedofsharesmarketlistener.dto.MarketEvent;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@AllArgsConstructor
@Component
public class MarketEventProcessorStrategy {
  private final Map<MarketEvent.Operation, MarketEventProcessor> processors;

  public void process(MarketEvent marketEvent) {
    log.info("Try process event = {}", marketEvent);
    Optional.ofNullable(processors.get(marketEvent.operation()))
        .orElseThrow(
            () -> new RuntimeException(
                "Can't find processor for event = %s".formatted(marketEvent)
            )
        )
        .process(marketEvent);
  }
}
