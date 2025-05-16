package com.liverpool.user.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class User {

  @Id
  private String id;

  private String name;
  private String lastName;
  private String secondLastName;
  private String email;

  private Address address;

}

