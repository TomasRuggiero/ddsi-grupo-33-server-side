package com.example.ddsigrupo33serverside.Exceptions;

public class DuplicateCorreoException extends RuntimeException{

  public DuplicateCorreoException(String correo) {
    super("El correo " + correo +  " ya existe");
  }

}
