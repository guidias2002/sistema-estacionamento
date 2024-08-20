package com.estacionamento.mapper;

import com.estacionamento.DTO.VeiculoDto;
import com.estacionamento.domain.Veiculo;
import org.springframework.stereotype.Component;

@Component
public class VeiculoMapper {

    public static VeiculoDto toDto(Veiculo veiculo) {
        return new VeiculoDto(
                veiculo.getId(),
                veiculo.getTipoVeiculo(),
                veiculo.getPlaca(),
                veiculo.getModelo(),
                veiculo.getCor(),
                veiculo.getEntrada(),
                veiculo.getSaida(),
                veiculo.getPeriodoEmMinutos(),
                veiculo.getValor()
        );
    }
}
