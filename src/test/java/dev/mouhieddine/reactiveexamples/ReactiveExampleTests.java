package dev.mouhieddine.reactiveexamples;

import dev.mouhieddine.reactiveexamples.commands.PersonCommand;
import dev.mouhieddine.reactiveexamples.domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author : Mouhieddine.dev
 * @since : 12/31/2020, Thursday
 **/
@Slf4j

public class ReactiveExampleTests {

  Person john = new Person("John David", "Washington");
  Person robert = new Person("Robert", "Pattinson");
  Person michael = new Person("Michael", "Caine");
  Person andrew = new Person("Andrew", "Howard");

  @Test
  public void monoTests() throws Exception {
    //create new person mono
    Mono<Person> personMono = Mono.just(john);

    //get person object from mono publisher
    Person person = personMono.block();

    // output name
    log.info(person.sayMyName());
  }

  @Test
  public void monoTransform() throws Exception {
    //create new person mono
    Mono<Person> personMono = Mono.just(robert);

    PersonCommand command = personMono
            .map(person -> { //type transformation
              return new PersonCommand(person);
            }).block();

    log.info(command.sayMyName());
  }

  @Test
  public void monoFilter() throws Exception {
    Mono<Person> personMono = Mono.just(michael);

    //filter example
    Person samAxe = personMono
            .filter(person -> person.getFirstName().equalsIgnoreCase("foo"))
            .block();


    assertThrows(NullPointerException.class, () -> {
      log.info(samAxe.sayMyName()); //throws NPE
    });
  }

  @Test
  public void fluxTest() throws Exception {

    Flux<Person> people = Flux.just(john, robert, michael, andrew);

    people.subscribe(person -> log.info(person.sayMyName()));

  }

  @Test
  public void fluxTestFilter() throws Exception {

    Flux<Person> people = Flux.just(john, robert, michael, andrew);

    people.filter(person -> person.getFirstName().equals(robert.getFirstName()))
            .subscribe(person -> log.info(person.sayMyName()));

  }

  @Test
  public void fluxTestDelayNoOutput() throws Exception {

    Flux<Person> people = Flux.just(john, robert, michael, andrew);

    people.delayElements(Duration.ofSeconds(1))
            .subscribe(person -> log.info(person.sayMyName()));

  }

  @Test
  public void fluxTestDelay() throws Exception {

    CountDownLatch countDownLatch = new CountDownLatch(1);

    Flux<Person> people = Flux.just(john, robert, michael, andrew);

    people.delayElements(Duration.ofSeconds(1))
            .doOnComplete(countDownLatch::countDown)
            .subscribe(person -> log.info(person.sayMyName()));

    countDownLatch.await();

  }

  @Test
  public void fluxTestFilterDelay() throws Exception {

    CountDownLatch countDownLatch = new CountDownLatch(1);

    Flux<Person> people = Flux.just(john, robert, michael, andrew);

    people.delayElements(Duration.ofSeconds(1))
            .filter(person -> person.getFirstName().contains("i"))
            .doOnComplete(countDownLatch::countDown)
            .subscribe(person -> log.info(person.sayMyName()));

    countDownLatch.await();
  }

}