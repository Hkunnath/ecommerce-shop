package com.example.ecommerceshop.globalException;

import com.example.ecommerceshop.cart.exception.CartNotFoundException;
import com.example.ecommerceshop.order.exception.OrderNotFoundException;
import com.example.ecommerceshop.product.exception.InsufficientStockException;
import com.example.ecommerceshop.product.exception.ProductNotFoundException;
import com.example.ecommerceshop.user.dto.response.ErrorDetails;
import com.example.ecommerceshop.user.exception.UserAlreadyExistException;
import com.example.ecommerceshop.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler({UserNotFoundException.class})
  public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ErrorDetails(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value()));
  }

  @ExceptionHandler({UserAlreadyExistException.class})
  public ResponseEntity<Object> handleUserAlreadyExistException(
      UserAlreadyExistException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ErrorDetails(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value()));
  }

  @ExceptionHandler({ProductNotFoundException.class})
  public ResponseEntity<Object> handleProductNotException(ProductNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ErrorDetails(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value()));
  }

  @ExceptionHandler({InsufficientStockException.class})
  public ResponseEntity<Object> handleInsufficientStockException(
      InsufficientStockException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ErrorDetails(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value()));
  }

  @ExceptionHandler({CartNotFoundException.class})
  public ResponseEntity<Object> handleCartNotFoundException(CartNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ErrorDetails(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value()));
  }

  @ExceptionHandler({OrderNotFoundException.class})
  public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ErrorDetails(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value()));
  }
}
