package it.di3mat.sesplib.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

  @Value("${app.timezone}")
  private String timeZone;

  @Bean
  public ObjectMapper objectMapper(){
    return new ObjectMapper().setTimeZone(TimeZone.getTimeZone(timeZone));
  }
}
