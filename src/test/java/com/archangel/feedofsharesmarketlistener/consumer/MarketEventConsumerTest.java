package com.archangel.feedofsharesmarketlistener.consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.archangel.feedofsharesmarketlistener.BaseIntegrationTest;
import com.archangel.feedofsharesmarketlistener.dto.MarketEvent;
import com.archangel.feedofsharesmarketlistener.dto.MarketEvent.Operation;
import com.archangel.feedofsharesmarketlistener.entity.UserShare;
import com.archangel.feedofsharesmarketlistener.repository.UserShareRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;

class MarketEventConsumerTest extends BaseIntegrationTest {
  private static final String DEFAULT_TICKER = "APPL";
  private static final String DEFAULT_USERNAME = "john";

  @Autowired
  private MarketEventConsumer marketEventConsumer;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private UserShareRepository userShareRepository;

  @DisplayName(
      """
        When user tries to buy not first shares with specific ticker
        Then we should update existing user shares, increasing it
        on passed amount in event
      """
  )
  @Test
  void process_WhenBuyOperation_Ok() {
    // Given
    createUserShareWithAmount(100);
    Message message = buildMessage(Operation.BUY);

    // When
    marketEventConsumer.onMessage(message);

    // Then
    UserShare userShare = userShareRepository.findByUsernameAndTicker(DEFAULT_USERNAME, DEFAULT_TICKER)
        .get();
    assertEquals(400, userShare.getAmount());
  }

  @DisplayName(
      """
        When user tries to buy not first shares with specific ticker
        Then we should update existing user shares, increasing it
        on passed amount in event
      """
  )
  @Test
  void process_WhenSellOperation_Ok() {
    // Given
    createUserShareWithAmount(500);
    Message message = buildMessage(Operation.SELL);

    // When
    marketEventConsumer.onMessage(message);

    // Then
    UserShare userShare = userShareRepository.findByUsernameAndTicker(DEFAULT_USERNAME, DEFAULT_TICKER)
        .get();
    assertEquals(200, userShare.getAmount());
  }

  private void createUserShareWithAmount(int amount) {
    userShareRepository.save(
        new UserShare()
            .setUsername(DEFAULT_USERNAME)
            .setUpdateTime(LocalDateTime.now())
            .setAmount(amount)
            .setTicker(DEFAULT_TICKER)
    );
  }

  @SneakyThrows
  private Message buildMessage(Operation operation) {
    MarketEvent marketEvent = new MarketEvent("john", "APPL", operation, 300);
    return new Message(objectMapper.writeValueAsBytes(marketEvent));
  }
}