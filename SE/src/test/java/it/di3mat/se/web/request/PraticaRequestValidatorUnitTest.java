package it.di3mat.se.web.request;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import it.di3mat.sesplib.exception.SespException;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

public class PraticaRequestValidatorUnitTest {

  @Test
  public void validatePraticaRequest_validate_valid_create_request(){
    // Given
    var request =
        PraticaCreateRequest.builder()
            .name("name_test")
            .surname("surname_test")
            .dateOfBirth(new Date())
            .taxCode("ABCDEFGHIL")
            .file(new MockMultipartFile("file_test", "original_file_test", null, new byte[]{}))
            .build();
    //When
    PraticaRequestValidator.validatePraticaRequest(request);
  }

  @Test
  public void validatePraticaRequest_throw_exception_with_empty_request(){
    // Given
    var request = PraticaCreateRequest.builder().build();
    //When
    var expected = assertThrows(SespException.class, ()->PraticaRequestValidator.validatePraticaRequest(request));
    //Then
    assertNotNull(expected);
  }

  @Test
  public void validatePraticaRequest_throw_exception_with_correct_request_but_blank_original_file_name(){
    // Given
    var request =
        PraticaCreateRequest.builder()
            .name("name_test")
            .surname("surname_test")
            .dateOfBirth(new Date())
            .taxCode("ABCDEFGHIL")
            .file(new MockMultipartFile("file_test", null, null, new byte[]{}))
            .build();
    //When
    var expected = assertThrows(SespException.class, ()->PraticaRequestValidator.validatePraticaRequest(request));
    //Then
    assertNotNull(expected);
  }
}
