package br.com.fiap.quartos.msquarto.repository;

import br.com.fiap.quartos.msquarto.domain.Localidade;
import br.com.fiap.quartos.msquarto.domain.Propriedade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropriedadeRepository extends JpaRepository<Propriedade, Long> {
    Page<Propriedade> findByLocalidade(Localidade localidadeId, Pageable pageable);
}
