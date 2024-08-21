package com.estacionamento.domain;

import com.estacionamento.DTO.VeiculoDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "veiculos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "tipoveiculo")
    @NotNull(message = "tipoVeiculo is required.")
    private VeiculoTipo tipoVeiculo;

    @Pattern(regexp = "^[A-Z]{3}-?\\d{4}$|^[A-Z]{3}\\d[A-Z]\\d{2}$",
            message = "Placa inválida. Deve seguir o formato ABC-1234 ou ABC1D23.")
    @NotBlank(message = "placa is required")
    private String placa;

    @NotBlank(message = "modelo is required")
    private String modelo;

    @NotBlank(message = "cor is required")
    private String cor;

    private LocalDateTime entrada;

    private LocalDateTime saida;

    @Column(name = "periodoemminutos")
    private Long periodoEmMinutos;

    private String valor;


    public Veiculo(VeiculoDto veiculo) {
        this.id = veiculo.id();
        this.tipoVeiculo = veiculo.tipoveiculo();
        this.placa = veiculo.placa();
        this.modelo = veiculo.modelo();
        this.cor = veiculo.cor();
        this.entrada = LocalDateTime.now();
        this.saida = veiculo.saida();
        this.valor = veiculo.valor();
    }
}
