package it.di3mat.sp.web;

import it.di3mat.sp.domain.Pratica;
import it.di3mat.sp.service.PraticaService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/v1/pratica")
public class SpWebApiController {

  @Autowired
  PraticaService praticaService;

  @GetMapping("/{id}")
  public ResponseEntity<Pratica> getPraticaDetails(@PathVariable("id") UUID id){
    return ResponseEntity.ok(praticaService.findPraticaById(id));
  }

  @GetMapping("/file/{id}")
  public ResponseEntity<Resource> downloadPraticaFile(@PathVariable("id") UUID id){
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .body(praticaService.getFile(id));
  }

  @PostMapping("/promote/")
  public ResponseEntity<UUID> promotePratica(@RequestParam("id") UUID id, @RequestParam(required = false) Boolean accepted){
    return ResponseEntity.ok(praticaService.promotePratica(id, accepted));
  }

}
