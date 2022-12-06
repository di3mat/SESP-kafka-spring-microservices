package it.di3mat.sp.domain;

import static it.di3mat.sp.domain.PraticaStatus.APPROVED;
import static it.di3mat.sp.domain.PraticaStatus.CREATED;
import static it.di3mat.sp.domain.PraticaStatus.REFUSED;
import static java.util.Objects.nonNull;

import com.sun.istack.NotNull;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pratica")
public class Pratica {

  @Id
  @Column(name = "id")
  private UUID idPratica;

  @Column(name = "name")
  private String name;

  @Column(name = "surname")
  private String surname;

  @Column(name = "date_of_birth")
  private Date dateOfBirth;

  @Column(name = "tax_code")
  private String taxCode;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private PraticaStatus status;

  @Lob
  @Column(name = "file")
  byte[] file;

  @Column(name = "created_by", nullable = false, updatable = false)
  private String createdBy;

  public Boolean isUpdatable(){
    return CREATED.equals(this.status);
  }

  public Boolean isPromotable(){
    return !REFUSED.equals(this.status)
        && !APPROVED.equals(this.status);
  }

  public Pratica promote(@NotNull Boolean accepted) {
    this.setStatus(this.getStatus().nextStatus(accepted));
    return this;
  }

  public Pratica merge(Pratica other) {
    if(nonNull(other.getName())){
      this.setName(other.getName());
    }
    if(nonNull(other.getSurname())){
      this.setSurname(other.getSurname());
    }
    if(nonNull(other.getTaxCode())){
      this.setTaxCode(other.getTaxCode());
    }
    if(nonNull(other.getDateOfBirth())){
      this.setDateOfBirth(other.getDateOfBirth());
    }
    if(nonNull(other.getFile())){
      this.setFile(other.getFile());
    }
    return this;
  }
}
