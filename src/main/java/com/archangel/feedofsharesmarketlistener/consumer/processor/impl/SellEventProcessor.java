package com.archangel.feedofsharesmarketlistener.consumer.processor.impl;

import com.archangel.feedofsharesmarketlistener.consumer.processor.MarketEventProcessor;
import com.archangel.feedofsharesmarketlistener.dto.MarketEvent;
import com.archangel.feedofsharesmarketlistener.dto.MarketEvent.Operation;
import com.archangel.feedofsharesmarketlistener.service.UserShareService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@AllArgsConstructor
@Component
public class SellEventProcessor implements MarketEventProcessor {
  private final UserShareService userShareService;

  @Override
  public void process(MarketEvent marketEvent) {
    userShareService.findByUsernameAndTicker(
        marketEvent.username(),
        marketEvent.ticker()
    ).filter(share -> share.getAmount() >= marketEvent.amount())
        .map(share -> share.setAmount(share.getAmount() - marketEvent.amount()))
        .map(userShareService::save)
        .ifPresentOrElse(
            share -> log.info("User's share was updated: {}", share),
            () -> log.error(
                "User {} doesn't have enough amount = {} of shares = {}",
                marketEvent.username(),
                marketEvent.amount(),
                marketEvent.ticker()

            )
        );
  }

  @Override
  public Operation getOperation() {
    return Operation.SELL;
  }
}
