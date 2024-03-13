package br.com.fiap.quartos.msquarto.repository;

import br.com.fiap.quartos.msquarto.domain.Localidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalidadeRepository extends JpaRepository<Localidade, Long> {
}
