package it.di3mat.sesplib.kafka;

import static java.util.Objects.nonNull;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage {

  private String author;
  private UUID idPratica;
  private String name;
  private String surname;
  private Date dateOfBirth;
  private String taxCode;
  private String status;
  byte[] file;

  public Boolean isValidNew(){
    return nonNull(idPratica)
        && nonNull(name)
        && nonNull(surname)
        && nonNull(dateOfBirth)
        && nonNull(taxCode)
        && nonNull(file);
  }
}
