package br.com.fiap.clientes.mscliente.repository;


import br.com.fiap.clientes.mscliente.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
