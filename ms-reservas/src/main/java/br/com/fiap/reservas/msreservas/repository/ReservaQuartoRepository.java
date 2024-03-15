package br.com.fiap.reservas.msreservas.repository;

import br.com.fiap.reservas.msreservas.domain.ReservaQuarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservaQuartoRepository extends JpaRepository<ReservaQuarto, Long> {
    @Query(value = "SELECT rq.id_quarto AS idQuarto " +
            "FROM RESERVA_QUARTO rq " +
            "INNER JOIN reservas r ON rq.codigo_reserva = r.codigo_reserva " +
            "WHERE :checkinRequest between r.checkin AND r.checkout " +
            "OR :checkoutRequest between r.checkin AND r.checkout ",
            nativeQuery = true)
    List<Long> findQuartosDisponiveisBetweenCheckinAndCheckout(LocalDate checkinRequest, LocalDate checkoutRequest);
}

