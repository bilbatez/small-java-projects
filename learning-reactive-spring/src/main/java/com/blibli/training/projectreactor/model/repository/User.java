package com.blibli.training.projectreactor.model.repository;

import com.blibli.training.projectreactor.model.controller.request.UserRequest;
import com.blibli.training.projectreactor.model.controller.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("users")
public class User {
  public static final String COLLECTION = "users";

  @Id
  private String id;
  private String username;

  public User(String username) {
    this.username = username;
  }

  public UserResponse toUserResponse() {
    return UserResponse.builder()
        .id(getId())
        .username(getUsername())
        .build();
  }
}
