package it.di3mat.se.web.request;

import static it.di3mat.sesplib.exception.SespException.throwSespException;

import javax.validation.Validation;

public class PraticaRequestValidator {

  public static void validatePraticaRequest(PraticaRequest request) {
    var factory = Validation.buildDefaultValidatorFactory();
    var validator = factory.getValidator();
    var violations = validator.validate(request);
    if(!violations.isEmpty()){
      var buffer = new StringBuilder().append("\n");
      for(var violation : violations){
        buffer.append(violation.getMessage()).append("\n");
      }
      throwSespException("Passed Request is not valid: {}", buffer.toString());
    }
  }

}
