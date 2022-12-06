package it.di3mat.sesplib.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

  @Bean
  public NewTopic topicPraticaNotifications(){
    return TopicBuilder.name("pratica-notifications").build();
  }

  @Bean
  public NewTopic topicPraticaUpdate(){
    return TopicBuilder.name("pratica-update").build();
  }
}
