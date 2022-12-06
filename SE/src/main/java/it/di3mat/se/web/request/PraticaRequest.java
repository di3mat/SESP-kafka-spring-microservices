package it.di3mat.se.web.request;

import java.util.Date;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface PraticaRequest {

  UUID getIdPratica();
  String getName();
  String getSurname();
  String getTaxCode();
  Date getDateOfBirth();
  MultipartFile getFile();
}
