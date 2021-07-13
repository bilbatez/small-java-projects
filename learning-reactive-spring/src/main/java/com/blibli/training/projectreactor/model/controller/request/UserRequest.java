package com.blibli.training.projectreactor.model.controller.request;

import com.blibli.training.projectreactor.model.repository.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
  private String id;
  private String username;

  public UserRequest(String username) {
    this.username = username;
  }

  public User toUser() {
    return User.builder()
        .id(getId())
        .username(getUsername())
        .build();
  }
}
