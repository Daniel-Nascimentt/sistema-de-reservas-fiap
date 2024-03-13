package br.com.fiap.quartos.msquarto.repository;

import br.com.fiap.quartos.msquarto.domain.Propriedade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropriedadeRepository extends JpaRepository<Propriedade, Long> {
}
