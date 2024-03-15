package br.com.fiap.reservas.msreservas.repository;

import br.com.fiap.reservas.msreservas.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservaRepository extends JpaRepository<Reserva, UUID> {
}
