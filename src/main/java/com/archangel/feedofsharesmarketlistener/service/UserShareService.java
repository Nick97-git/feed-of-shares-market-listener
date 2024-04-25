package com.archangel.feedofsharesmarketlistener.service;

import com.archangel.feedofsharesmarketlistener.entity.UserShare;
import java.util.List;
import java.util.Optional;

public interface UserShareService {
  UserShare save(UserShare userShare);

  Optional<UserShare> findByUsernameAndTicker(String username, String ticker);

  List<UserShare> findAllByUsername(String username);
}
