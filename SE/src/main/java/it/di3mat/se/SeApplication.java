package it.di3mat.se;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@ComponentScan({"it.di3mat.se", "it.di3mat.sesplib"})
@SpringBootApplication
public class SeApplication {

  public static void main(String[] args) {
    SpringApplication.run(SeApplication.class, args);
  }
}
