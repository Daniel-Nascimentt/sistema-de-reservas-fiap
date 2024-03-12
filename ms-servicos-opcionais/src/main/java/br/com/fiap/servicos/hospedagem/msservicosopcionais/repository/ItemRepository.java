package br.com.fiap.servicos.hospedagem.msservicosopcionais.repository;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
