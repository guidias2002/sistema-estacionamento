package com.estacionamento.controllers;

import com.estacionamento.DTO.VeiculoDto;
import com.estacionamento.DTO.VeiculoSaidaDto;
import com.estacionamento.domain.Veiculo;
import com.estacionamento.services.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    public ResponseEntity<Veiculo> registrarVeiculo(@RequestBody @Valid VeiculoDto veiculo) {
        Veiculo newVeiculo = this.veiculoService.registrarVeiculo(veiculo);

        return ResponseEntity.ok(newVeiculo);
    }

    @GetMapping
    public ResponseEntity<List<VeiculoDto>> listarTodosOsVeiculos() {
        List<VeiculoDto> veiculos = this.veiculoService.listarTodosOsVeiculos();

        return ResponseEntity.ok(veiculos);
    }

    @GetMapping("/estacionados")
    public ResponseEntity<List<VeiculoDto>> listarVeiculosEstacionados() {
        List<VeiculoDto> veiculos = this.veiculoService.listarVeiculosEstacionados();

        return ResponseEntity.ok(veiculos);
    }

    @PostMapping("/saida/{placa}")
    public ResponseEntity<VeiculoSaidaDto> registrarSaidaVeiculo(@PathVariable String placa) {
        VeiculoSaidaDto dadosVeiculo = this.veiculoService.registrarSaidaVeiculo(placa);

        return ResponseEntity.ok(dadosVeiculo);
    }

    @GetMapping("/registros-por-veiculo/{placa}")
    public ResponseEntity<List<VeiculoDto>> listarRegistrosDeUmVeiculo(@PathVariable String placa){
        List<VeiculoDto> veiculoRegistros = this.veiculoService.listarRegistrosDeUmVeiculo(placa);

        return ResponseEntity.ok(veiculoRegistros);
    }
}
