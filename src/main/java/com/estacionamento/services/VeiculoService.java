package com.estacionamento.services;

import com.estacionamento.DTO.VeiculoDto;
import com.estacionamento.DTO.VeiculoSaidaDto;
import com.estacionamento.domain.Veiculo;
import com.estacionamento.mapper.VeiculoMapper;
import com.estacionamento.mapper.VeiculoSaidaMapper;
import com.estacionamento.repositories.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final VeiculoMapper veiculoMapper;
    private final VeiculoSaidaMapper veiculoSaidaMapper;

    public VeiculoService(VeiculoRepository veiculoRepository, VeiculoMapper veiculoMapper, VeiculoSaidaMapper veiculoSaidaMapper) {
        this.veiculoRepository = veiculoRepository;
        this.veiculoMapper = veiculoMapper;
        this.veiculoSaidaMapper = veiculoSaidaMapper;
    }

    public Veiculo registrarVeiculo(VeiculoDto veiculo){
        Veiculo newVeiculo = new Veiculo(veiculo);

        this.veiculoRepository.save(newVeiculo);

        return newVeiculo;
    }

    public List<VeiculoDto> listarTodosOsVeiculos(){
        return this.veiculoRepository.findAll().stream()
                .map(VeiculoMapper::toDto)
                .toList();
    }

    public List<VeiculoDto> listarVeiculosEstacionados(){
        return this.veiculoRepository.findBySaidaNull().stream()
                .map(VeiculoMapper::toDto)
                .toList();
    }

    public VeiculoSaidaDto registrarSaidaVeiculo(String placa){
        Veiculo veiculo = this.veiculoRepository.findVeiculoByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Nenhum veículo encontrado com essa placa."));

        veiculo.setSaida(LocalDateTime.now());

        Long periodo = calcularPeriodoEmMinuto(veiculo.getEntrada(), veiculo.getSaida());
        String calculoValor = calcularValor(veiculo.getEntrada(), veiculo.getSaida());

        veiculo.setValor(calculoValor);
        veiculo.setPeriodoEmMinutos(periodo);

        this.veiculoRepository.save(veiculo);

        return VeiculoSaidaMapper.toDto(veiculo);
    }

    public String calcularValor(LocalDateTime entrada, LocalDateTime saida){
        Long periodoEmMinutos = calcularPeriodoEmMinuto(entrada, saida);

        if(periodoEmMinutos <= 120){
            return "R$10,00";
        }

        if(periodoEmMinutos > 120 && periodoEmMinutos <= 240){
            return "R$20,00";
        }

        if(periodoEmMinutos > 240){
            return "R$30,00";
        }

        return null;
    }

    public Long calcularPeriodoEmMinuto(LocalDateTime entrada, LocalDateTime saida){
        Duration periodo = Duration.between(entrada, saida);

        Long periodoEmMinutos = periodo.toMinutes();

        return periodoEmMinutos;
    }
}
