package com.estacionamento.infra.exceptions;

public class VehicleIsParkedException extends RuntimeException {
    public VehicleIsParkedException(String message) {
        super(message);
    }
}
