package com.estacionamento.mapper;

import com.estacionamento.DTO.VeiculoSaidaDto;
import com.estacionamento.domain.Veiculo;
import org.springframework.stereotype.Component;

@Component
public class VeiculoSaidaMapper {
    public static VeiculoSaidaDto toDto(Veiculo veiculo){
        return new VeiculoSaidaDto(
                veiculo.getTipoVeiculo(),
                veiculo.getPlaca(),
                veiculo.getModelo(),
                veiculo.getEntrada(),
                veiculo.getSaida(),
                veiculo.getPeriodoEmMinutos(),
                veiculo.getValor()
        );
    }
}
