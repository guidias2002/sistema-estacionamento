package com.estacionamento.infra.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
public class TaskErrorMessage {
    private String statusCode;
    private Map<String, String> errors;

    public TaskErrorMessage(HttpStatus statusCode, Map<String, String> errors) {
        this.statusCode = statusCode.toString();
        this.errors = errors;
    }
}
