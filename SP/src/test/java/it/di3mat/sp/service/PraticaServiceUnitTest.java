package it.di3mat.sp.service;

import static it.di3mat.sp.domain.PraticaStatus.APPROVED;
import static it.di3mat.sp.domain.PraticaStatus.IN_PROGRESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import it.di3mat.sesplib.kafka.KafkaMessage;
import it.di3mat.sesplib.exception.SespException;
import it.di3mat.sesplib.kafka.KafkaConnection;
import it.di3mat.sp.domain.Pratica;
import it.di3mat.sp.domain.PraticaStatus;
import it.di3mat.sp.repository.PraticaRepository;
import java.sql.Date;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.helpers.MessageFormatter;

@ExtendWith(MockitoExtension.class)
public class PraticaServiceUnitTest {

  @Mock
  PraticaRepository repository;
  @Mock
  KafkaConnection kafkaConnection;
  @InjectMocks PraticaService praticaService;


  @Test()
  public void findPraticaById_throw_exception_when_pratica_is_not_found(){
    //Given
    var id = UUID.randomUUID();
    when(repository.findById(id)).thenReturn(Optional.empty());
    //When
    var result = assertThrows(SespException.class, ()->praticaService.findPraticaById(id));
    //Then
    assertNotNull(result);
  }

  @Test()
  public void newPratica_throw_exception_when_new_pratica_has_null_field() {
    //Given
    var dto = KafkaMessage.builder().build();
    //When
    var result = assertThrows(SespException.class, ()->praticaService.newPratica(dto));
    // Then
    assertEquals("The given new Pratica has not all the mandatory fields", result.getMessage());
  }

  @Test()
  public void newPratica_create_pratica_from_given_valid_dto() {
    // Given
    var validDto =
        KafkaMessage.builder()
            .idPratica(UUID.randomUUID())
            .name("name")
            .surname("surname")
            .dateOfBirth(Date.valueOf("2022-03-03"))
            .taxCode("taxCode")
            .file(new byte[]{})
            .build();
    // When
    var result = praticaService.newPratica(validDto);
    // Then
    assertEquals(result.getIdPratica(), validDto.getIdPratica());
    assertEquals(result.getName(), validDto.getName());
    assertEquals(result.getSurname(), validDto.getSurname());
    assertEquals(result.getDateOfBirth(), validDto.getDateOfBirth());
    assertEquals(result.getTaxCode(), validDto.getTaxCode());
    assertEquals(result.getStatus(), PraticaStatus.CREATED);
    assertEquals(result.getFile(), validDto.getFile());
  }

  @Test()
  public void updatePratica_throw_exception_when_pratica_not_updatable() {
    // Given
    var id = UUID.randomUUID();
    var dto =
        KafkaMessage.builder()
            .idPratica(id)
            .name("new_name")
            .surname("new_surname")
            .build();
    var pratica =
        Pratica.builder().idPratica(id).name("name").surname("surname").status(IN_PROGRESS).build();
    when(repository.findById(id)).thenReturn(Optional.of(pratica));
    // When
    var result = assertThrows(SespException.class, ()->praticaService.updatePratica(dto));
    // Then
    var expectedMessage =
        MessageFormatter.arrayFormat(
                "Pratica {} is not updatable. Status: {}",
                new Object[] {pratica.getIdPratica(), pratica.getStatus()})
            .getMessage();
    assertEquals(expectedMessage, result.getMessage());
  }

  @Test()
  public void promotePratica_throw_exception_when_pratica_not_promotable() {
    // Given
    var id = UUID.randomUUID();
    var pratica =
        Pratica.builder().idPratica(id).name("name").surname("surname").status(APPROVED).build();
    when(repository.findById(id)).thenReturn(Optional.of(pratica));
    // When
    var result = assertThrows(SespException.class, ()->praticaService.promotePratica(id, null));
    // Then
    var expectedMessage =
        MessageFormatter.arrayFormat(
                "Pratica {} is already completed",
                new Object[] {pratica.getIdPratica()})
            .getMessage();
    assertEquals(expectedMessage, result.getMessage());
  }

}
