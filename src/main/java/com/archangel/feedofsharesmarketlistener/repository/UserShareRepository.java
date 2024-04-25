package com.archangel.feedofsharesmarketlistener.repository;

import com.archangel.feedofsharesmarketlistener.entity.UserShare;
import com.archangel.feedofsharesmarketlistener.entity.UserShareId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserShareRepository extends JpaRepository<UserShare, UserShareId> {
  Optional<UserShare> findByUsernameAndTicker(String username, String ticker);

  List<UserShare> findAllByUsername(String username);
}
