package br.com.fiap.quartos.msquarto.repository;

import br.com.fiap.quartos.msquarto.domain.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuartoRepository extends JpaRepository<Quarto, Long> {
}
