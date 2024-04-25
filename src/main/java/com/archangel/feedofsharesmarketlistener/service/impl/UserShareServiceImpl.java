package com.archangel.feedofsharesmarketlistener.service.impl;

import com.archangel.feedofsharesmarketlistener.entity.UserShare;
import com.archangel.feedofsharesmarketlistener.repository.UserShareRepository;
import com.archangel.feedofsharesmarketlistener.service.UserShareService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserShareServiceImpl implements UserShareService {
  private final UserShareRepository userShareRepository;

  @Override
  public UserShare save(UserShare userShare) {
    return userShareRepository.save(userShare);
  }

  @Override
  public Optional<UserShare> findByUsernameAndTicker(String username, String ticker) {
    return userShareRepository.findByUsernameAndTicker(username, ticker);
  }

  @Override
  public List<UserShare> findAllByUsername(String username) {
    return userShareRepository.findAllByUsername(username);
  }
}
