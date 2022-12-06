package it.di3mat.sesplib.exception;

import org.slf4j.helpers.MessageFormatter;

public class SespException extends RuntimeException{

  public SespException(String message){
    super(message);
  }

  public static SespException createSespException(String format, Object... arguments) {
    String message = MessageFormatter.arrayFormat(format, arguments).getMessage();
    return new SespException(message);
  }

  public static SespException throwSespException(String format, Object... arguments) {
    throw  createSespException(format, arguments);
  }
}
