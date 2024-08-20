package com.estacionamento.repositories;

import com.estacionamento.domain.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VeiculoRepository extends JpaRepository<Veiculo, UUID> {
    Optional<Veiculo> findVeiculoByPlaca(String placa);
    List<Veiculo> findBySaidaNull();
}
