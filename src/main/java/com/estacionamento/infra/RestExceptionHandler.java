package com.estacionamento.infra;

import com.estacionamento.infra.exceptions.TaskErrorMessage;
import com.estacionamento.infra.exceptions.TaskErrorMessageNotFound;
import com.estacionamento.infra.exceptions.VehicleIsParkedException;
import com.estacionamento.infra.exceptions.VehicleNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        // Criação da lista de erros a partir dos detalhes da exceção
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage
                ));

        TaskErrorMessage taskErrorMessage = new TaskErrorMessage(HttpStatus.BAD_REQUEST, errors);

        return new ResponseEntity<>(taskErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String errorMessage = "Invalid value";

        Map<String, String> errors = new HashMap<>();
        errors.put("tipoveiculo", errorMessage);

        TaskErrorMessage taskErrorMessage = new TaskErrorMessage(HttpStatus.BAD_REQUEST, errors);

        return new ResponseEntity<>(taskErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<TaskErrorMessageNotFound> handleVehicleNotFoundException(VehicleNotFoundException ex){
        TaskErrorMessageNotFound taskErrorMessageNotFound = new TaskErrorMessageNotFound(HttpStatus.NOT_FOUND, ex.getMessage());

        return new ResponseEntity<>(taskErrorMessageNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VehicleIsParkedException.class)
    public ResponseEntity<TaskErrorMessageNotFound> handleVehicleIsParkedException(VehicleIsParkedException ex){
        TaskErrorMessageNotFound taskErrorMessageNotFound = new TaskErrorMessageNotFound(HttpStatus.CONFLICT, ex.getMessage());

        return new ResponseEntity<>(taskErrorMessageNotFound, HttpStatus.CONFLICT);
    }
}
