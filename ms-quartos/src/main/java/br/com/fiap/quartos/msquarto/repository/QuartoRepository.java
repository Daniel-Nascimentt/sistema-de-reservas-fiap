package br.com.fiap.quartos.msquarto.repository;

import br.com.fiap.quartos.msquarto.domain.Quarto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuartoRepository extends JpaRepository<Quarto, Long> {

    @Query(value = "SELECT q.id AS quartoId, q.tipo_quarto AS tipoQuarto, q.descricao_quarto AS descricaoQuarto, "
            + "b.id AS banheiroId, b.descricao_banheiro AS descricaoBanheiro, b.tipo_banheiro AS tipoBanheiro, "
            + "p.nome_propriedade AS nomePropriedade, p.endereco_propriedade AS enderecoPropriedade, "
            + "l.nome_localidade AS nomeLocalidade "
            + "FROM quartos q "
            + "INNER JOIN banheiros b ON b.id = q.banheiro_id "
            + "INNER JOIN propriedades_quartos pq ON pq.quarto_id = q.id "
            + "INNER JOIN propriedades p ON p.id = pq.propriedade_id "
            + "INNER JOIN localidades l ON p.localidade_id = l.id "
            + "WHERE l.id = :localidadeId",
            nativeQuery = true)
    Page<QuartoProjection> findAllQuartosByLocalidade(@Param("localidadeId") Long localidadeId, Pageable pageable);

}
