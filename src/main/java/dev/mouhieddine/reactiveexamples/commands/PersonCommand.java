package dev.mouhieddine.reactiveexamples.commands;

import dev.mouhieddine.reactiveexamples.domain.Person;
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
public class PersonCommand {

  private String firstName;
  private String lastName;
  public PersonCommand(Person person) {
    this.firstName = person.getFirstName();
    this.lastName = person.getLastName();
  }

  public String sayMyName() {
    return "My Name is " + firstName + " " + lastName + ".";
  }
}