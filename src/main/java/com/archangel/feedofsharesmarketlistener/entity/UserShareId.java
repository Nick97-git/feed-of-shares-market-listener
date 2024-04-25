package com.archangel.feedofsharesmarketlistener.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserShareId implements Serializable {
  private String username;
  private String ticker;
}
