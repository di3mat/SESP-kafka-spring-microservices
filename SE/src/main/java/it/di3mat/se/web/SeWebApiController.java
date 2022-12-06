package it.di3mat.se.web;

import static it.di3mat.se.web.request.PraticaRequestValidator.validatePraticaRequest;
import static it.di3mat.sesplib.exception.SespException.createSespException;
import static java.util.Objects.nonNull;

import it.di3mat.se.service.SpConnectionService;
import it.di3mat.se.web.request.PraticaCreateRequest;
import it.di3mat.se.web.request.PraticaRequest;
import it.di3mat.se.web.request.PraticaUpdateRequest;
import it.di3mat.sesplib.kafka.KafkaMessage;
import it.di3mat.sesplib.kafka.KafkaConnection;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/pratica")
public class SeWebApiController {

  @Autowired KafkaConnection kafkaConnection;
  @Autowired SpConnectionService spConnection;

  @GetMapping("/")
  public ResponseEntity<PraticaResponse> getPraticaDetails(@RequestParam("id") UUID id) {
    return ResponseEntity.ok(spConnection.getPraticaDetails(id));
  }

  @GetMapping("/file")
  public ResponseEntity<Resource> downloadPraticaFile(@RequestParam("id") UUID id) {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .body(spConnection.getPraticaFile(id));
  }

  @PostMapping("/new")
  public ResponseEntity<String> newPratica(@ModelAttribute PraticaCreateRequest request) {
    validatePraticaRequest(request);
    request.setIdPratica(UUID.randomUUID());
    var pratica = fromPraticaRequestToKafkaMessage(request);
    kafkaConnection.sendMessage("pratica-create", pratica);
    return ResponseEntity.ok(pratica.getIdPratica().toString());
  }

  @PostMapping("/update")
  public ResponseEntity<String> updatePratica(@ModelAttribute PraticaUpdateRequest request) {
    validatePraticaRequest(request);
    var pratica = fromPraticaRequestToKafkaMessage(request);
    kafkaConnection.sendMessageWithKey(
        "pratica-update", pratica.getIdPratica().toString(), pratica);
    return ResponseEntity.ok(pratica.getIdPratica().toString());
  }

  public KafkaMessage fromPraticaRequestToKafkaMessage(PraticaRequest request) {
    var auth = SecurityContextHolder.getContext().getAuthentication().getName();
    var km =
        KafkaMessage.builder()
            .author(auth)
            .idPratica(request.getIdPratica())
            .name(request.getName())
            .surname(request.getSurname())
            .taxCode(request.getTaxCode())
            .dateOfBirth(request.getDateOfBirth())
            .build();
    if (nonNull(request.getFile())) {
      try {
        km.setFile(request.getFile().getBytes());
      } catch (IOException e) {
        throw createSespException(e.getMessage());
      }
    }
    return km;
  }

}
