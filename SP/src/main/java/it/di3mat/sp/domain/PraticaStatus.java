package it.di3mat.sp.domain;

import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public enum PraticaStatus {
  CREATED,
  IN_PROGRESS,
  APPROVED,
  REFUSED;

  public PraticaStatus nextStatus(@NotNull Boolean accepted) {
    switch(this){
      case CREATED:
        return IN_PROGRESS;
      case IN_PROGRESS:
        return accepted ? APPROVED : REFUSED;
      default:
        return this;
    }
  }
}
