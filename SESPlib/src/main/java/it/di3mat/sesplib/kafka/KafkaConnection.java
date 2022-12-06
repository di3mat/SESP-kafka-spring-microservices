package it.di3mat.sesplib.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaConnection {

  private KafkaTemplate<String, Object> kafkaTemplate;

  @Autowired
  KafkaConnection(KafkaTemplate<String, Object> kafkaTemplate){
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessageWithKey(String topicName, String key, Object data){
    kafkaTemplate.send(topicName, key, data);
  }

  public void sendMessage(String topicName, Object data){
    kafkaTemplate.send(topicName, data);
  }

}
