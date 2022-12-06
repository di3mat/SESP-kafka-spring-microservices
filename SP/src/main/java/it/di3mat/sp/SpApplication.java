package it.di3mat.sp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@ComponentScan({"it.di3mat.sp", "it.di3mat.sesplib"})
@EnableJpaRepositories("it.di3mat.sp.repository")
@SpringBootApplication
public class SpApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpApplication.class, args);
  }

}
