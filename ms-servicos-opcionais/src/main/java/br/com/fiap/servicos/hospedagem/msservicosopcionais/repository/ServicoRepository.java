package br.com.fiap.servicos.hospedagem.msservicosopcionais.repository;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.domain.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    Page<Servico> findByIdPropriedade(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM SERVICOS  WHERE ID IN (:ids)", nativeQuery = true)
    Page<Servico> findByListIds(@Param("ids") List<Long> ids, Pageable pageable);
}
