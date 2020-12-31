package dev.mouhieddine.reactiveexamples.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Mouhieddine.dev
 * @since : 12/31/2020, Thursday
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

  private String firstName;
  private String lastName;

  public String sayMyName() {
    return "My Name is " + firstName + " " + lastName + ".";
  }

}