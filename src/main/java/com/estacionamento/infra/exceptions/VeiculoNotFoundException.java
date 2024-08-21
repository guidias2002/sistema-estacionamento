package com.estacionamento.infra.exceptions;

public class VeiculoNotFoundException extends RuntimeException {
    public VeiculoNotFoundException() {
        super("Nenhum veículo encontrado com essa placa.");
    }

    public VeiculoNotFoundException(String message) {
        super(message);
    }
}
