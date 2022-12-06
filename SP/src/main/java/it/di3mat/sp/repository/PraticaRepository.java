package it.di3mat.sp.repository;

import it.di3mat.sp.domain.Pratica;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PraticaRepository extends JpaRepository<Pratica, UUID> {

}
