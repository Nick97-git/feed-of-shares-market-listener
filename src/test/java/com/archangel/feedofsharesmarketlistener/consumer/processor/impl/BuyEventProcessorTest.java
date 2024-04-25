package com.archangel.feedofsharesmarketlistener.consumer.processor.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.archangel.feedofsharesmarketlistener.dto.MarketEvent;
import com.archangel.feedofsharesmarketlistener.dto.MarketEvent.Operation;
import com.archangel.feedofsharesmarketlistener.entity.UserShare;
import com.archangel.feedofsharesmarketlistener.mapper.UserShareMapper;
import com.archangel.feedofsharesmarketlistener.service.UserShareService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BuyEventProcessorTest {
  private static final String DEFAULT_USERNAME = "john";
  private static final String DEFAULT_TICKER = "APPL";

  @Mock
  private UserShareMapper userShareMapper;
  @Mock
  private UserShareService userShareService;
  @InjectMocks
  private BuyEventProcessor buyEventProcessor;

  @Test
  void process_WhenNoSharesWithTickerForUserBefore_Ok() {
    // Given
    MarketEvent marketEvent = createMarketEvent();

    when(userShareService.findByUsernameAndTicker(marketEvent.username(), marketEvent.ticker()))
        .thenReturn(Optional.empty());

    UserShare userShare = new UserShare()
        .setTicker(marketEvent.ticker())
        .setAmount(marketEvent.amount())
        .setUsername(marketEvent.username());
    when(userShareMapper.mapToUserShare(marketEvent))
        .thenReturn(userShare);

    // When
    buyEventProcessor.process(marketEvent);

    // Then
    verify(userShareService).save(userShare);
  }

  @Test
  void process_WhenSharesWithTickerForUserExist_Ok() {
    // Given
    MarketEvent marketEvent = createMarketEvent();

    when(userShareService.findByUsernameAndTicker(marketEvent.username(), marketEvent.ticker()))
        .thenReturn(
            Optional.of(
                new UserShare()
                    .setUsername(marketEvent.username())
                    .setAmount(400)
                    .setTicker(marketEvent.ticker())
            )
        );

    // When
    buyEventProcessor.process(marketEvent);

    // Then
    int expectedAmount = 700;
    UserShare userShare = new UserShare()
        .setTicker(marketEvent.ticker())
        .setAmount(expectedAmount)
        .setUsername(marketEvent.username());

    verify(userShareService).save(userShare);
    assertEquals(expectedAmount, userShare.getAmount());
  }

  @Test
  void getOperation_Ok() {
    // When
    Operation actual = buyEventProcessor.getOperation();

    // Then
    assertEquals(Operation.BUY, actual);
  }

  private MarketEvent createMarketEvent() {
    return new MarketEvent(
        DEFAULT_USERNAME,
        DEFAULT_TICKER,
        Operation.BUY,
        300
    );
  }
}