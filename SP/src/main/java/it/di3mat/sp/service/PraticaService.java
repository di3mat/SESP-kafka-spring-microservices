package it.di3mat.sp.service;

import static it.di3mat.sesplib.exception.SespException.createSespException;
import static it.di3mat.sesplib.exception.SespException.throwSespException;
import static java.util.Objects.isNull;

import it.di3mat.sesplib.kafka.KafkaMessage;
import it.di3mat.sesplib.kafka.KafkaConnection;
import it.di3mat.sp.domain.Pratica;
import it.di3mat.sp.domain.PraticaStatus;
import it.di3mat.sp.repository.PraticaRepository;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PraticaService {

  private final String LOG_TAG = this.getClass().getSimpleName();

  @Autowired private PraticaRepository repository;
  @Autowired private KafkaConnection kafkaConnection;

  public Pratica newPratica(KafkaMessage dto) {
    if (!dto.isValidNew()) {
      throw createSespException("The given new Pratica has not all the mandatory fields");
    }
    var id = dto.getIdPratica();
    var newPratica =
        Pratica.builder()
            .idPratica(id)
            .name(dto.getName())
            .surname(dto.getSurname())
            .taxCode(dto.getTaxCode())
            .dateOfBirth(dto.getDateOfBirth())
            .status(PraticaStatus.CREATED)
            .file(dto.getFile())
            .createdBy(dto.getAuthor())
            .build();
    saveAndNotify(newPratica);
    log.info(
        "[{}] Create Pratica {} is completed successfully", LOG_TAG, newPratica.getIdPratica());
    return newPratica;
  }

  public Pratica updatePratica(KafkaMessage dto) {
    var id = dto.getIdPratica();
    var oldPratica = this.findPraticaById(id);
    if (!oldPratica.isUpdatable()) {
      throwSespException(
          "Pratica {} is not updatable. Status: {}",
          oldPratica.getIdPratica(),
          oldPratica.getStatus());
    }
    var updatedPratica =
        Pratica.builder()
            .idPratica(id)
            .name(dto.getName())
            .surname(dto.getSurname())
            .taxCode(dto.getTaxCode())
            .dateOfBirth(dto.getDateOfBirth())
            .file(dto.getFile())
            .build();
    repository.save(oldPratica.merge(updatedPratica));
    log.info(
        "[{}] Update Pratica {} is completed successfully", LOG_TAG, updatedPratica.getIdPratica());
    return updatedPratica;
  }

  public UUID promotePratica(UUID id, Boolean accepted) {
    var pratica = findPraticaById(id);
    if (!pratica.isPromotable()) {
      throwSespException("Pratica {} is already completed", id);
    }
    if (PraticaStatus.IN_PROGRESS.equals(pratica.getStatus()) && isNull(accepted)) {
      throwSespException("Promotion of a Pratica to an ended status must has the 'accepted' field");
    }
    saveAndNotify(pratica.promote(accepted));
    return id;
  }

  public Resource getFile(UUID id) {
    var pratica = findPraticaById(id);
    return new ByteArrayResource(pratica.getFile());
  }

  public Pratica findPraticaById(UUID idPratica) {
    return repository
        .findById(idPratica)
        .orElseThrow(
            () -> createSespException("[{}] Pratica with id {} not found", LOG_TAG, idPratica));
  }

  public Pratica saveAndNotify(Pratica pratica) {
    repository.save(pratica);
    kafkaConnection.sendMessageWithKey(
        "pratica-notifications",
        pratica.getIdPratica().toString(),
        String.format("Pratica %s: %s", pratica.getIdPratica().toString(), pratica.getStatus()));
    return pratica;
  }
}
