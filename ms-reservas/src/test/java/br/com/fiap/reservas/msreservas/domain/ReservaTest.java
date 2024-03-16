package br.com.fiap.reservas.msreservas.domain;

import br.com.fiap.reservas.msreservas.exception.ClienteInvalidoException;
import br.com.fiap.reservas.msreservas.exception.DataCheckinInvalidaException;
import br.com.fiap.reservas.msreservas.exception.OperacaoReservaNaoPermitidaException;
import br.com.fiap.reservas.msreservas.request.ItemRequest;
import br.com.fiap.reservas.msreservas.request.NovaReservaRequest;
import br.com.fiap.reservas.msreservas.request.ServicoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ReservaTest {

    @Mock
    ReservaQuarto reservaQuartoMock;

    @Mock
    OpcionaisReserva opcionaisReservaMock;

    Reserva reserva;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        reserva = new Reserva(LocalDate.now(), LocalDate.now().plusDays(1), LocalDateTime.now().minusDays(1), 1L);
    }

    @Test
    void addOpcional() {
        reserva.addOpcional(opcionaisReservaMock);
        assertEquals(1, reserva.getServicosOpcionais().size());
    }

    @Test
    void addQuartoAReserva() {
        reserva.addQuartoAReserva(reservaQuartoMock);
        assertEquals(1, reserva.getQuartos().size());
    }

    @Test
    void somarAoTotalReserva() {
        reserva.somarAoTotalReserva(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, reserva.getValorTotalReserva());
    }

    @Test
    void confirmarReserva() {
        when(reservaQuartoMock.getStatusQuarto()).thenReturn(StatusQuarto.RESERVADO);
        reserva.addQuartoAReserva(reservaQuartoMock);
        reserva.confirmarReserva();
        assertTrue(reserva.getQuartos().stream().allMatch(q -> q.getStatusQuarto().equals(StatusQuarto.RESERVADO)));
    }

    @Test
    void validarStatusReservado() {
        when(reservaQuartoMock.getStatusQuarto()).thenReturn(StatusQuarto.RESERVADO);
        reserva.addQuartoAReserva(reservaQuartoMock);
        reserva.confirmarReserva();
        assertThrows(OperacaoReservaNaoPermitidaException.class, () -> reserva.validarStatusReservado());
    }

    @Test
    void clearOpcional() {
        reserva.addOpcional(opcionaisReservaMock);
        reserva.clearOpcional();
        assertTrue(reserva.getServicosOpcionais().isEmpty());
    }

    @Test
    void clearQuartos() {
        reserva.addQuartoAReserva(reservaQuartoMock);
        reserva.clearQuartos();
        assertTrue(reserva.getQuartos().isEmpty());
    }

    @Test
    void zerarValorDiaria() {
        reserva.somarAoTotalReserva(BigDecimal.TEN);
        reserva.zerarValorDiaria();
        assertEquals(BigDecimal.ZERO, reserva.getValorTotalReserva());
    }

    @Test
    void atualizarPreReserva() throws DataCheckinInvalidaException {
        LocalDateTime dataAnterior = reserva.getDataPreReserva();

        NovaReservaRequest novaReservaRequest = new NovaReservaRequest(Arrays.asList(1L), Arrays.asList(new ServicoRequest()), Arrays.asList(new ItemRequest()), 1L, LocalDate.now(), LocalDate.now().plusDays(1));

        reserva.atualizarPreReserva(novaReservaRequest);

        assertNotEquals(dataAnterior, reserva.getDataPreReserva());
    }

    @Test
    void validarTitularidade() {
        assertDoesNotThrow(() -> reserva.validarTitularidade(1L));
        assertThrows(ClienteInvalidoException.class, () -> reserva.validarTitularidade(2L));
    }
}
