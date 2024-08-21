package com.estacionamento.DTO;

import com.estacionamento.domain.VeiculoTipo;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.UUID;

public record VeiculoDto (
        UUID id,
        @NotNull(message = "tipoveiculo is required")
        VeiculoTipo tipoveiculo,
        @Pattern(regexp = "^[A-Z]{3}-?\\d{4}$|^[A-Z]{3}\\d[A-Z]\\d{2}$",
                message = "Placa inválida. Deve seguir o formato ABC-1234 ou ABC1D23.")
        @NotBlank(message = "placa is required")
        String placa,
        @NotBlank(message = "modelo is required")
        String modelo,
        @NotBlank(message = "cor is required")
        String cor,
        LocalDateTime entrada,
        LocalDateTime saida,
        Long periodoEmMinutos,
        String valor) {
}
