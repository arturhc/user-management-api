package com.liverpool.user.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

  private String street;
  private String state;
  private String neighborhood;
  private String exteriorNumber;
  private String city;
  private String zipCode;

}
