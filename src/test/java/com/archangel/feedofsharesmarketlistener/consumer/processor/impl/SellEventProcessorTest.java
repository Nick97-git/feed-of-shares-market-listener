package com.archangel.feedofsharesmarketlistener.consumer.processor.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.archangel.feedofsharesmarketlistener.dto.MarketEvent;
import com.archangel.feedofsharesmarketlistener.dto.MarketEvent.Operation;
import com.archangel.feedofsharesmarketlistener.entity.UserShare;
import com.archangel.feedofsharesmarketlistener.service.UserShareService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SellEventProcessorTest {
  private static final String DEFAULT_USERNAME = "john";
  private static final String DEFAULT_TICKER = "APPL";

  @Mock
  private UserShareService userShareService;
  @InjectMocks
  private SellEventProcessor sellEventProcessor;

  @Test
  void process_WhenNoSharesForTicker_ShouldNotUpdateUserShare() {
    // Given
    MarketEvent marketEvent = createMarketEvent(500);

    when(userShareService.findByUsernameAndTicker(marketEvent.username(), marketEvent.ticker()))
        .thenReturn(Optional.empty());

    // When
    sellEventProcessor.process(marketEvent);

    // Then
    verifyNoMoreInteractions(userShareService);
  }

  @Test
  void process_WhenNotEnoughSharesForTicker_ShouldNotUpdateUserShare() {
    // Given
    MarketEvent marketEvent = createMarketEvent(100);

    when(userShareService.findByUsernameAndTicker(marketEvent.username(), marketEvent.ticker()))
        .thenReturn(
            Optional.of(
                new UserShare()
                    .setAmount(99)
                    .setUsername(DEFAULT_USERNAME)
                    .setTicker(DEFAULT_TICKER))
        );

    // When
    sellEventProcessor.process(marketEvent);

    // Then
    verifyNoMoreInteractions(userShareService);
  }

  @CsvSource({"400, 100", "500, 0"})
  @ParameterizedTest(name = "Need to sell {0} ticker and leave on account {1}")
  void process_WhenEnoughSharesForTicker_ShouldUpdateUserShare(int amountToSell, int amountAfterSell) {
    // Given
    MarketEvent marketEvent = createMarketEvent(amountToSell);

    when(userShareService.findByUsernameAndTicker(marketEvent.username(), marketEvent.ticker()))
        .thenReturn(
            Optional.of(
                new UserShare()
                    .setAmount(500)
                    .setUsername(DEFAULT_USERNAME)
                    .setTicker(DEFAULT_TICKER)
            )
        );

    // When
    sellEventProcessor.process(marketEvent);

    // Then
    verify(userShareService)
        .save(new UserShare().setAmount(amountAfterSell).setUsername(DEFAULT_USERNAME).setTicker(
            DEFAULT_TICKER));
  }

  @Test
  void getOperation_Ok() {
    // When
    Operation actual = sellEventProcessor.getOperation();

    // Then
    assertEquals(Operation.SELL, actual);
  }

  private MarketEvent createMarketEvent(int amount) {
    return new MarketEvent(
        DEFAULT_USERNAME,
        DEFAULT_TICKER,
        Operation.SELL,
        amount
    );
  }
}