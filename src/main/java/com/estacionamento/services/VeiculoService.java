package com.estacionamento.services;

import com.estacionamento.DTO.VeiculoDto;
import com.estacionamento.DTO.VeiculoSaidaDto;
import com.estacionamento.domain.Veiculo;
import com.estacionamento.repositories.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    public Veiculo registrarVeiculo(VeiculoDto veiculo){
        Veiculo newVeiculo = new Veiculo(veiculo);

        this.veiculoRepository.save(newVeiculo);

        return newVeiculo;
    }

    public List<VeiculoDto> listarVeiculos(){
        return this.veiculoRepository.findAll().stream()
                .map((veiculo) -> new VeiculoDto(veiculo.getId(), veiculo.getTipoVeiculo(), veiculo.getPlaca(), veiculo.getModelo(), veiculo.getCor(), veiculo.getEntrada(), veiculo.getSaida(), veiculo.getPeriodoEmMinutos(),veiculo.getValor()))
                .toList();
    }

    public VeiculoSaidaDto saidaVeiculo(String placa){
        Veiculo veiculo = this.veiculoRepository.findVeiculoByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Nenhum veículo encontrado com essa placa."));

        veiculo.setSaida(LocalDateTime.now());

        Long periodo = calcularPeriodoEmMinuto(veiculo.getEntrada(), veiculo.getSaida());
        String calculoValor = calcularValor(veiculo.getEntrada(), veiculo.getSaida());

        veiculo.setValor(calculoValor);
        veiculo.setPeriodoEmMinutos(periodo);

        this.veiculoRepository.save(veiculo);

        return new VeiculoSaidaDto(veiculo.getTipoVeiculo(), veiculo.getPlaca(), veiculo.getModelo(), veiculo.getEntrada(), veiculo.getSaida(), veiculo.getPeriodoEmMinutos(), veiculo.getValor());
    }

    public String calcularValor(LocalDateTime entrada, LocalDateTime saida){
        Long periodoEmMinutos = calcularPeriodoEmMinuto(entrada, saida);

        if(periodoEmMinutos <= 120){
            return "R$10,00";
        }

        if(periodoEmMinutos > 120 && periodoEmMinutos < 240){
            return "R$20,00";
        }

        return null;
    }

    public Long calcularPeriodoEmMinuto(LocalDateTime entrada, LocalDateTime saida){
        Duration periodo = Duration.between(entrada, saida);

        Long periodoEmMinutos = periodo.toMinutes();

        return periodoEmMinutos;
    }
}
