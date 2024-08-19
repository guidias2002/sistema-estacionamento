package com.estacionamento.controllers;

import com.estacionamento.DTO.VeiculoDto;
import com.estacionamento.DTO.VeiculoSaidaDto;
import com.estacionamento.domain.Veiculo;
import com.estacionamento.services.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<VeiculoDto>> listarVeiculos() {
        List<VeiculoDto> veiculos = this.veiculoService.listarVeiculos();

        return ResponseEntity.ok(veiculos);
    }

    @PostMapping("/saida/{placa}")
    public ResponseEntity<VeiculoSaidaDto> saidaVeiculo(@PathVariable String placa){
        VeiculoSaidaDto dadosVeiculo = this.veiculoService.saidaVeiculo(placa);

        return ResponseEntity.ok(dadosVeiculo);
    }
}
