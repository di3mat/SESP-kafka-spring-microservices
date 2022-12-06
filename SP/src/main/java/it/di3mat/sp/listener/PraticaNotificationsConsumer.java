package it.di3mat.sp.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PraticaNotificationsConsumer {

  private final String LOG_TAG = this.getClass().getSimpleName();

  @KafkaListener(topics = "pratica-notifications", groupId = "sp-consumer-group")
  public void statoPraticheListener(String notification) {
    log.info("[{}] {}", LOG_TAG, notification);
  }
}
