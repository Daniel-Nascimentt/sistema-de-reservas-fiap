package br.com.fiap.servicos.hospedagem.msservicosopcionais.repository;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.domain.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    Page<Servico> findByIdHotel(Long id, Pageable pageable);
}
