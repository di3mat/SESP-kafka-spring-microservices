package it.di3mat.sp.listener;

import it.di3mat.sesplib.kafka.KafkaMessage;
import it.di3mat.sp.service.PraticaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PraticaUpdateConsumer {

  @Autowired private PraticaService praticaService;

  @KafkaListener(topics = "pratica-update", groupId = "sp-consumer-group")
  public void updatePratica(KafkaMessage dto){
    praticaService.updatePratica(dto);
  }

  @KafkaListener(topics = "pratica-create", groupId = "sp-consumer-group")
  public void createPratica(KafkaMessage dto){
    praticaService.newPratica(dto);
  }

}
