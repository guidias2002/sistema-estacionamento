package com.estacionamento.infra.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class TaskErrorMessageNotFound {
    private String statusCode;
    private String message;

    public TaskErrorMessageNotFound(HttpStatus statusCode, String message) {
        this.statusCode = statusCode.toString();
        this.message = message;
    }
}
