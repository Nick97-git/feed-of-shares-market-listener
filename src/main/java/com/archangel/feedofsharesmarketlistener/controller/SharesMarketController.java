package com.archangel.feedofsharesmarketlistener.controller;

import com.archangel.feedofsharesmarketlistener.dto.MarketEvent;
import com.archangel.feedofsharesmarketlistener.dto.UserShareDto;
import com.archangel.feedofsharesmarketlistener.mapper.UserShareMapper;
import com.archangel.feedofsharesmarketlistener.producer.MarketEventProducer;
import com.archangel.feedofsharesmarketlistener.service.UserShareService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@AllArgsConstructor
@RequestMapping("/shares")
@RestController
public class SharesMarketController {
  private final MarketEventProducer marketEventProducer;
  private final UserShareMapper userShareMapper;
  private final UserShareService userShareService;

  @GetMapping
  public List<UserShareDto> findAllSharesByUsername(@RequestParam("username") String username) {
    return userShareService.findAllByUsername(username).stream()
        .map(userShareMapper::mapToUserShareDto)
        .toList();
  }

  @PostMapping("/event")
  public ResponseEntity<HttpEntity<Void>> sendMarketEvent(@RequestBody MarketEvent event) {
    marketEventProducer.sendEvent(event);
    return ResponseEntity.ok().build();
  }
}
