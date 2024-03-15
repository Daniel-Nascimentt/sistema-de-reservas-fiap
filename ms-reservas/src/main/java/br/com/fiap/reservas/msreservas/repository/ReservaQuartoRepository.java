package br.com.fiap.reservas.msreservas.repository;

import br.com.fiap.reservas.msreservas.domain.ReservaQuarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservaQuartoRepository extends JpaRepository<ReservaQuarto, Long> {
    @Query(value = "SELECT dq.id AS idQuarto " +
            "FROM RESERVA_QUARTO dq " +
            "INNER JOIN reservas r ON dq.reserva_codigo_reserva = r.codigo_reserva " +
            "WHERE r.checkin >= :checkinRequest AND r.checkout <= :checkoutRequest",
            nativeQuery = true)
    List<Long> findQuartosDisponiveisBetweenCheckinAndCheckout(LocalDate checkinRequest, LocalDate checkoutRequest);
}

