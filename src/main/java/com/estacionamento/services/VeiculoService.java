package com.estacionamento.services;

import com.estacionamento.DTO.VeiculoDto;
import com.estacionamento.DTO.VeiculoSaidaDto;
import com.estacionamento.DTO.VeiculoUpdateDto;
import com.estacionamento.domain.Veiculo;
import com.estacionamento.infra.exceptions.VehicleIsParkedException;
import com.estacionamento.infra.exceptions.VehicleNotFoundException;
import com.estacionamento.mapper.VeiculoMapper;
import com.estacionamento.mapper.VeiculoSaidaMapper;
import com.estacionamento.mapper.VeiculoUpdateMapper;
import com.estacionamento.repositories.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        Optional<Veiculo> veiculoJaCadastradoOpt = this.veiculoRepository.findByPlacaAndSaidaIsNull(newVeiculo.getPlaca());

        if(veiculoJaCadastradoOpt.isPresent()){
            Veiculo veiculoJaCadastrado = veiculoJaCadastradoOpt.get();

            if(veiculoJaCadastrado.getSaida() == null){
                throw new VehicleIsParkedException("Veículo com a placa " + veiculoJaCadastrado.getPlaca() + " já está estacionado");
            }
        }

        this.veiculoRepository.save(newVeiculo);

        return newVeiculo;
    }

    public List<VeiculoDto> listarTodosOsVeiculos(){
        return this.veiculoRepository.findAll().stream()
                .map(VeiculoMapper::toDto)
                .sorted(Comparator.comparing(VeiculoDto::saida, Comparator.nullsFirst(Comparator.naturalOrder())))
                .toList();
    }

    public List<VeiculoDto> listarVeiculosEstacionados(){
        return this.veiculoRepository.findBySaidaNull().stream()
                .map(VeiculoMapper::toDto)
                .sorted(Comparator.comparing(VeiculoDto::entrada).reversed())
                .toList();
    }

    public VeiculoSaidaDto registrarSaidaVeiculo(String placa){
        Veiculo veiculo = this.veiculoRepository.findByPlacaAndSaidaIsNull(placa)
                .orElseThrow(() -> new VehicleNotFoundException());

        veiculo.setSaida(LocalDateTime.now());

        Integer periodo = calcularPeriodoEmMinuto(veiculo.getEntrada(), veiculo.getSaida());
        String calculoValor = calcularValor(veiculo.getEntrada(), veiculo.getSaida(), String.valueOf(veiculo.getTipoVeiculo()));

        veiculo.setValor(calculoValor);
        veiculo.setPeriodoEmMinutos(periodo);

        this.veiculoRepository.save(veiculo);

        return VeiculoSaidaMapper.toDto(veiculo);
    }

    public void excluirVeiculo(UUID id){
        Veiculo veiculo = this.veiculoRepository.findVeiculoById(id)
                .orElseThrow(() -> new VehicleNotFoundException());

        this.veiculoRepository.deleteById(id);
    }

    public VeiculoDto atualizarVeiculo(UUID id, VeiculoUpdateDto veiculoUpt){
        Veiculo veiculo = this.veiculoRepository.findVeiculoById(id)
                .orElseThrow(() -> new VehicleNotFoundException());

        if(veiculo.getSaida() == null){

            if(veiculoUpt.tipoveiculo() != null){
                veiculo.setTipoVeiculo(veiculoUpt.tipoveiculo());
            }

            if(veiculoUpt.placa() != null){
                veiculo.setPlaca(veiculoUpt.placa());
            }

            if(veiculoUpt.modelo() != null){
                veiculo.setModelo(veiculoUpt.modelo());
            }

            if(veiculoUpt.cor() != null){
                veiculo.setCor(veiculoUpt.cor());
            }

            this.veiculoRepository.save(veiculo);
        }


        return VeiculoMapper.toDto(veiculo);
    }

    public String calcularValor(LocalDateTime entrada, LocalDateTime saida, String tipoVeiculo){
        Integer periodoEmMinutos = calcularPeriodoEmMinuto(entrada, saida);

        Integer valorAte120Min;
        Integer valorAte240Min;
        Integer valor30Min;

        if(tipoVeiculo.equalsIgnoreCase("carro")){
            valorAte120Min = 10;
            valorAte240Min = 20;
            valor30Min = 5;
        } else if(tipoVeiculo.equalsIgnoreCase("moto")){
            valorAte120Min = 5;
            valorAte240Min = 10;
            valor30Min = 3;
        } else {
            return "Tipo de veículo inválido.";
        }

        if (periodoEmMinutos <= 120) {
            return "R$" + valorAte120Min + ",00";
        }

        if (periodoEmMinutos > 120 && periodoEmMinutos <= 240) {
            return "R$" + valorAte240Min + ",00";
        }

        if (periodoEmMinutos > 240) {
            Integer minutosExcedentes = periodoEmMinutos - 240;
            int intervalosAdicionais = (int) Math.ceil(minutosExcedentes / 30.0);
            Integer valorFinal = valorAte240Min + (intervalosAdicionais * valor30Min);

            return "R$" + valorFinal + ",00";
        }

        return null;
    }

    public Integer calcularPeriodoEmMinuto(LocalDateTime entrada, LocalDateTime saida){
        Duration periodo = Duration.between(entrada, saida);

        Integer periodoEmMinutos = Math.toIntExact(periodo.toMinutes());

        return periodoEmMinutos;
    }

    public List<VeiculoDto> listarRegistrosDeUmVeiculo(String placa){
        List<VeiculoDto> registrosVeiculos = this.veiculoRepository.findVeiculoByPlaca(placa).stream()
                .map(VeiculoMapper::toDto)
                .toList();

        if(registrosVeiculos.isEmpty()){
            throw new VehicleNotFoundException("Nenhum veículo encontrado com a placa " + placa);
        }

        return registrosVeiculos;
    }


















}
