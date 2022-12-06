package it.di3mat.sesplib.kafka;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Slf4j
@Configuration
public class KafkaProducerConfig {

  private final String LOG_TAG = "Kafka";
  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Bean
  public Map<String, Object> producerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
        bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        JsonSerializer.class);
    return props;
  }

  @Bean
  public ProducerFactory<String, Object> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigs());
  }

  @Bean
  public KafkaTemplate<String, Object> kafkaTemplate() {
    var kafkaTemplate =  new KafkaTemplate<>(producerFactory());
    kafkaTemplate.setProducerListener(new ProducerListener<String, Object>() {
      @Override
      public void onSuccess(
          ProducerRecord<String, Object> producerRecord,
          RecordMetadata recordMetadata) {

        log.debug("[{}] [topic: {}] [key: {}] PRODUCE MESSAGE -> success",
            LOG_TAG,
            producerRecord.topic(),
            producerRecord.key());
      }

      @Override
      public void onError(ProducerRecord<String, Object> producerRecord, RecordMetadata recordMetadata,
          Exception exception) {
        log.error("[{}] [topic: {}] [key: {}] PRODUCE MESSAGE -> error. Exception: {}",
            LOG_TAG,
            producerRecord.topic(),
            producerRecord.key(),
            exception.getMessage());
      }
    });
    return kafkaTemplate;
  }
}
