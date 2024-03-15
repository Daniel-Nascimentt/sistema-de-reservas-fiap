package br.com.fiap.servicos.hospedagem.msservicosopcionais.repository;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findByIdPropriedade(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM ITENS WHERE ID IN (:ids)", nativeQuery = true)
    Page<Item> findByListId(@Param("ids") List<Long> ids, Pageable pageable);
}
