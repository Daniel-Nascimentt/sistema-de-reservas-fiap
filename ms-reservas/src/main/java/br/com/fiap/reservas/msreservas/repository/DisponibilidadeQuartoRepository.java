package br.com.fiap.reservas.msreservas.repository;

import br.com.fiap.reservas.msreservas.domain.DisponibilidadeQuarto;
import br.com.fiap.reservas.msreservas.domain.StatusQuarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DisponibilidadeQuartoRepository extends JpaRepository<DisponibilidadeQuarto, Long> {
    @Query(value = "SELECT dq.id AS idQuarto " +
            "FROM disponibilidade_quartos dq " +
            "INNER JOIN reservas r ON dq.reserva_codigo_reserva = r.codigo_reserva " +
            "WHERE r.checkin >= :checkinRequest AND r.checkout <= :checkoutRequest",
            nativeQuery = true)
    List<Long> findQuartosDisponiveisBetweenCheckinAndCheckout(LocalDate checkinRequest, LocalDate checkoutRequest);
}

