package com.archangel.feedofsharesmarketlistener.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MarketEvent(
    @NotBlank
    String username,
    @NotBlank
    String ticker,
    @NotNull
    Operation operation,
    @Min(1)
    int amount
) {
  @JsonCreator
  public MarketEvent(
      @JsonProperty String username,
      @JsonProperty String ticker,
      @JsonProperty Operation operation,
      @JsonProperty int amount
  ) {
    this.username = username;
    this.ticker = ticker;
    this.operation = operation;
    this.amount = amount;
  }

  public enum Operation {
    BUY, SELL
  }
}
