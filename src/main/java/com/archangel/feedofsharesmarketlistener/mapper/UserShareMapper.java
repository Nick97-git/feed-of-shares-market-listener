package com.archangel.feedofsharesmarketlistener.mapper;

import com.archangel.feedofsharesmarketlistener.dto.MarketEvent;
import com.archangel.feedofsharesmarketlistener.dto.UserShareDto;
import com.archangel.feedofsharesmarketlistener.entity.UserShare;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserShareMapper {
  public UserShare mapToUserShare(MarketEvent marketEvent) {
    UserShare userShare = new UserShare();
    BeanUtils.copyProperties(marketEvent, userShare);
    return userShare;
  }

  public UserShareDto mapToUserShareDto(UserShare userShare) {
    return new UserShareDto(
        userShare.getUsername(),
        userShare.getTicker(),
        userShare.getAmount(),
        userShare.getUpdateTime()
    );
  }
}
