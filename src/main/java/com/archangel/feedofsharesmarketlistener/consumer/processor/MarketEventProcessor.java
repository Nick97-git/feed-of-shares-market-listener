package com.archangel.feedofsharesmarketlistener.consumer.processor;

import com.archangel.feedofsharesmarketlistener.dto.MarketEvent;

public interface MarketEventProcessor {
  void process(MarketEvent marketEvent);

  MarketEvent.Operation getOperation();
}
