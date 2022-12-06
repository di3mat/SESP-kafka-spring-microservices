package it.di3mat.se.service;

import static it.di3mat.sesplib.exception.SespException.throwSespException;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class SpErrorHandler extends DefaultResponseErrorHandler {

  public void handleError(ClientHttpResponse response) throws IOException {
    if(HttpStatus.BAD_REQUEST.equals(response.getStatusCode())){
      throwSespException("Request is not valid for the Server");
    }else{
      super.handleError(response);
    }
  }
}
