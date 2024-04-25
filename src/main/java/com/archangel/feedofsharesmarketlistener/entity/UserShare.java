package com.archangel.feedofsharesmarketlistener.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Accessors(chain = true)
@IdClass(UserShareId.class)
@Entity
public class UserShare {
  @Id
  private String username;
  @Id
  private String ticker;
  private Integer amount;
  @UpdateTimestamp
  private LocalDateTime updateTime;
}
