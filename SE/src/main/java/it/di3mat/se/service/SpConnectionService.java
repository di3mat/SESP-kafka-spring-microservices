package it.di3mat.se.service;

import it.di3mat.se.web.PraticaResponse;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SpConnectionService {

  @Value("${app.service.sp.url}")
  String spBaseUrl;

  private final RestTemplate spRestTemplate;

  SpConnectionService() {
    spRestTemplate = new RestTemplate();
    spRestTemplate.setErrorHandler(new SpErrorHandler());
  }

  public PraticaResponse getPraticaDetails(UUID id) {
    return spRestTemplate.getForObject(spBaseUrl + "/v1/pratica/"+id, PraticaResponse.class);
  }

  public Resource getPraticaFile(UUID id) {
    return spRestTemplate.getForObject(spBaseUrl + "/v1/pratica/file/"+id, Resource.class);
  }
}
