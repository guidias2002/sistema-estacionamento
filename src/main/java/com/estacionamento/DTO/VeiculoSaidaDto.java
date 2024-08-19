package com.estacionamento.DTO;

import com.estacionamento.domain.VeiculoTipo;

import java.time.LocalDateTime;

public record VeiculoSaidaDto (VeiculoTipo tipoVeiculo, String placa, String modelo, LocalDateTime entrada, LocalDateTime saida, Long periodoEmMinutos, String valor) {
}
