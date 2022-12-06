package it.di3mat.se.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PraticaUpdateRequest implements PraticaRequest{

  @NotNull(message = "Pratica's ID is mandatory for the update")
  private UUID idPratica;

  private String name;

  private String surname;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
  private Date dateOfBirth;

  @Size(min=10, max = 32, message = "Tax code must be between 10 and 32 characters")
  private String taxCode;

  private MultipartFile file;

}
