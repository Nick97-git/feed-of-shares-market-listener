package com.archangel.feedofsharesmarketlistener.dto;

import java.time.LocalDateTime;

public record UserShareDto(
    String username,
    String ticker,
    int amount,
    LocalDateTime updateTime
) {
}
