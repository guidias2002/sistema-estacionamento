package com.estacionamento.DTO;

import com.estacionamento.domain.VeiculoTipo;
import jakarta.validation.constraints.Pattern;

public record VeiculoUpdateDto (
        VeiculoTipo tipoveiculo,
        @Pattern(regexp = "^[A-Z]{3}-?\\d{4}$|^[A-Z]{3}\\d[A-Z]\\d{2}$",
                message = "Placa inválida. Deve seguir o formato ABC-1234 ou ABC1D23.")
        String placa,
        String modelo,
        String cor
) {
}
