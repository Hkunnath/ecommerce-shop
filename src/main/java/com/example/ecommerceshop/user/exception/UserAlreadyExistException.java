package com.example.ecommerceshop.user.exception;

public class UserAlreadyExistException extends RuntimeException {
  public UserAlreadyExistException() {
    super("Username or emailId already exists");
  }
}
