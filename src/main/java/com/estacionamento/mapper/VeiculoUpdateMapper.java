package com.estacionamento.mapper;

import com.estacionamento.DTO.VeiculoDto;
import com.estacionamento.DTO.VeiculoUpdateDto;
import com.estacionamento.domain.Veiculo;
import org.springframework.stereotype.Component;

@Component
public class VeiculoUpdateMapper {

    public static VeiculoUpdateDto toDto(Veiculo veiculo) {
        return new VeiculoUpdateDto(
                veiculo.getTipoVeiculo(),
                veiculo.getPlaca(),
                veiculo.getModelo(),
                veiculo.getCor()
        );
    }
}
