package com.estacionamento.infra.exceptions;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException() {
        super("Nenhum veículo encontrado com essa placa.");
    }

    public VehicleNotFoundException(String message) {
        super(message);
    }
}
