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
public class PraticaCreateRequest implements PraticaRequest{

  private UUID idPratica;

  @NotNull(message = "'Name' field is mandatory")
  private String name;

  @NotNull(message = "'Surname' field is mandatory")
  private String surname;

  @NotNull(message = "'Date of birth' field is mandatory")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
  private Date dateOfBirth;

  @NotNull(message = "'Tax code' field is mandatory")
  @Size(min=10, max = 32, message = "Tax code must be between 10 and 32 characters")
  private String taxCode;

  @NotNull(message = "'File' is mandatory")
  private MultipartFile file;

}
