package it.di3mat.sesplib.web;

import it.di3mat.sesplib.exception.SespException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SespExceptionResponseHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(SespException.class)
  private ResponseEntity<Object> handleException(SespException e){
    return ResponseEntity.badRequest().body(e.getMessage());
  }
}
