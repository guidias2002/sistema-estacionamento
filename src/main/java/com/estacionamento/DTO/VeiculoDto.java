package com.estacionamento.DTO;

import com.estacionamento.domain.VeiculoTipo;

import java.time.LocalDateTime;
import java.util.UUID;

public record VeiculoDto (UUID id, VeiculoTipo tipoveiculo, String placa, String modelo, String cor, LocalDateTime entrada, LocalDateTime saida, Long periodoEmMinutos, String valor) {
}
