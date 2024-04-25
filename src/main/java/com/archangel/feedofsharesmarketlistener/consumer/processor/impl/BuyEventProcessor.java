package com.archangel.feedofsharesmarketlistener.consumer.processor.impl;

import com.archangel.feedofsharesmarketlistener.consumer.processor.MarketEventProcessor;
import com.archangel.feedofsharesmarketlistener.dto.MarketEvent;
import com.archangel.feedofsharesmarketlistener.dto.MarketEvent.Operation;
import com.archangel.feedofsharesmarketlistener.entity.UserShare;
import com.archangel.feedofsharesmarketlistener.mapper.UserShareMapper;
import com.archangel.feedofsharesmarketlistener.service.UserShareService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class BuyEventProcessor implements MarketEventProcessor {
  private final UserShareMapper userShareMapper;
  private final UserShareService userShareService;

  @Override
  public void process(MarketEvent marketEvent) {
    UserShare userShare = userShareService.findByUsernameAndTicker(
        marketEvent.username(),
        marketEvent.ticker()
    ).map(share -> share.setAmount(share.getAmount() + marketEvent.amount()))
        .orElseGet(() -> userShareMapper.mapToUserShare(marketEvent));
    userShareService.save(userShare);
  }

  @Override
  public Operation getOperation() {
    return Operation.BUY;
  }
}
