package br.com.fiap.reservas.msreservas.service;

import br.com.fiap.reservas.msreservas.client.MsClientesClient;
import br.com.fiap.reservas.msreservas.client.MsQuartosClient;
import br.com.fiap.reservas.msreservas.client.MsServicosClient;
import br.com.fiap.reservas.msreservas.domain.OpcionaisReserva;
import br.com.fiap.reservas.msreservas.domain.Reserva;
import br.com.fiap.reservas.msreservas.domain.TipoBanheiro;
import br.com.fiap.reservas.msreservas.domain.TipoQuarto;
import br.com.fiap.reservas.msreservas.exception.*;
import br.com.fiap.reservas.msreservas.repository.ReservaQuartoRepository;
import br.com.fiap.reservas.msreservas.repository.ReservaRepository;
import br.com.fiap.reservas.msreservas.request.ItemRequest;
import br.com.fiap.reservas.msreservas.request.NovaReservaRequest;
import br.com.fiap.reservas.msreservas.request.ServicoRequest;
import br.com.fiap.reservas.msreservas.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ReservaServiceTest {

    @Mock
    private MsQuartosClient msQuartosClient;

    @Mock
    private ReservaQuartoRepository reservaQuartoRepository;

    @Mock
    private MsServicosClient msServicosClient;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private MsClientesClient msClientesClient;

    @InjectMocks
    private ReservaService reservaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetQuartosDisponiveis() {
        Long idLocalidade = 1L;
        int quantidadeHospedes = 2;
        LocalDate checkin = LocalDate.now();
        LocalDate checkout = checkin.plusDays(1);

        List<QuartoResponse> quartos = Arrays.asList(
                fakeQuartoResponse(),
                fakeQuartoResponse()
        );

        when(msQuartosClient.getAllQuartosByLocalidade(idLocalidade)).thenReturn(new PageImpl<>(quartos));
        when(reservaQuartoRepository.findQuartosDisponiveisBetweenCheckinAndCheckout(checkin, checkout)).thenReturn(new ArrayList<>());

        Page<QuartoResponse> result = reservaService.getQuartosDisponiveis(idLocalidade, quantidadeHospedes, checkin, checkout);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(msQuartosClient, times(1)).getAllQuartosByLocalidade(idLocalidade);
        verify(reservaQuartoRepository, times(1)).findQuartosDisponiveisBetweenCheckinAndCheckout(checkin, checkout);
    }

    @Test
    public void testPreReservar() throws QuartoJaReservadoException, DataCheckinInvalidaException {
        NovaReservaRequest request = fakeRequest();

        List<QuartoResponse> quartos = Arrays.asList(
                fakeQuartoResponse(),
                fakeQuartoResponse()
        );

        List<ServicoResponse> servicos = Arrays.asList(
                new ServicoResponse(1L, "Serviço 1", BigDecimal.valueOf(50), 1L, 1L)
        );

        List<ItemResponse> itens = Arrays.asList(
                new ItemResponse(1L, "Item 1", BigDecimal.valueOf(20), 1L, 1L)
        );

        when(msQuartosClient.obterQuartosPorListIds(request.getIdsQuarto(), Pageable.unpaged())).thenReturn(new PageImpl<>(quartos));
        when(reservaQuartoRepository.findQuartosDisponiveisBetweenCheckinAndCheckout(request.getCheckin(), request.getCheckout())).thenReturn(new ArrayList<>());
        when(msServicosClient.obterServicoPorListaIds(any(), any())).thenReturn(new PageImpl<>(servicos));
        when(msServicosClient.obterItensPorListDeIds(any(), any())).thenReturn(new PageImpl<>(itens));
        when(msClientesClient.buscarClientePorId(request.getIdCliente())).thenReturn(fakeClienteResponse());
        when(msQuartosClient.obterQuartosPorListIds(any(), any())).thenReturn(new PageImpl<>(List.of(fakeQuartoResponse())));
        Reserva reserva = new Reserva(LocalDate.now(), LocalDate.now().plusDays(1), LocalDateTime.now(), request.getIdCliente());
        reserva.setCodigoReserva(UUID.randomUUID());
        reserva.getServicosOpcionais().add(new OpcionaisReserva(
            1L,
                "S_1",
                reserva,
                BigDecimal.TEN,
                2L
        ));
        reserva.getServicosOpcionais().add(new OpcionaisReserva(
            1L,
                "I_1",
                reserva,
                BigDecimal.TEN,
                2L
        ));
        when(reservaRepository.save(any())).thenReturn(reserva);

        ReservaResponse result = reservaService.preReservar(request);

        assertNotNull(result);
        verify(msQuartosClient, times(1)).obterQuartosPorListIds(request.getIdsQuarto(), Pageable.unpaged());
        verify(reservaQuartoRepository, times(1)).findQuartosDisponiveisBetweenCheckinAndCheckout(request.getCheckin(), request.getCheckout());
        verify(msServicosClient, times(1)).obterServicoPorListaIds(any(), any());
        verify(msServicosClient, times(1)).obterItensPorListDeIds(any(), any());
        verify(msClientesClient, times(1)).buscarClientePorId(request.getIdCliente());
        verify(reservaRepository, times(2)).save(any());
    }

    @Test
    public void testPreReservarQuartoJaReservadoException() throws DataCheckinInvalidaException {
        NovaReservaRequest request = fakeRequest();

        when(reservaQuartoRepository.findQuartosDisponiveisBetweenCheckinAndCheckout(request.getCheckin(), request.getCheckout()))
                .thenReturn(Arrays.asList(1L));

        assertThrows(QuartoJaReservadoException.class, () -> reservaService.preReservar(request));
        verify(reservaQuartoRepository, times(1)).findQuartosDisponiveisBetweenCheckinAndCheckout(request.getCheckin(), request.getCheckout());
        verify(msQuartosClient, never()).obterQuartosPorListIds(anyList(), any());
        verify(msServicosClient, never()).obterServicoPorListaIds(anyList(), any());
        verify(msServicosClient, never()).obterItensPorListDeIds(anyList(), any());
        verify(msClientesClient, never()).buscarClientePorId(anyLong());
        verify(reservaRepository, never()).save(any());
    }

    @Test
    void testAtualizarReserva() throws OperacaoReservaNaoPermitidaException, ClienteInvalidoException, ReservaNaoEncontradaException, DataCheckinInvalidaException {
        NovaReservaRequest request = fakeRequest();
        UUID codigoReserva = UUID.randomUUID();
        Reserva reserva = new Reserva(LocalDate.now(), LocalDate.now().plusDays(1), LocalDateTime.now(), request.getIdCliente());
        reserva.setCodigoReserva(codigoReserva);

        when(reservaRepository.findById(codigoReserva)).thenReturn(java.util.Optional.of(reserva));
        when(msClientesClient.buscarClientePorId(request.getIdCliente())).thenReturn(fakeClienteResponse());
        when(reservaRepository.save(any())).thenReturn(reserva);

        List<ServicoResponse> servicos = new ArrayList<>();
        List<ItemResponse> itens = new ArrayList<>();
        List<QuartoResponse> quartos = new ArrayList<>();

        when(msServicosClient.obterServicoPorListaIds(any(), any())).thenReturn(new PageImpl<>(servicos));
        when(msServicosClient.obterItensPorListDeIds(any(), any())).thenReturn(new PageImpl<>(itens));
        when(msQuartosClient.obterQuartosPorListIds(any(), any())).thenReturn(new PageImpl<>(quartos));

        ReservaResponse reservaResponse = reservaService.atualizarReserva(request, codigoReserva);
        assertNotNull(reservaResponse);

        verify(reservaRepository, times(1)).findById(any());
        verify(msClientesClient, times(1)).buscarClientePorId(any());
        verify(msServicosClient, times(1)).obterServicoPorListaIds(any(), any());
        verify(msServicosClient, times(1)).obterItensPorListDeIds(any(), any());
        verify(msQuartosClient, times(2)).obterQuartosPorListIds(any(), any());
        verify(reservaRepository, times(2)).save(any());
    }

    @Test
    void testDeletarReserva() throws ReservaNaoEncontradaException, OperacaoReservaNaoPermitidaException {
        UUID codigoReserva = UUID.randomUUID();
        Reserva reserva = new Reserva(LocalDate.now(), LocalDate.now().plusDays(1), LocalDateTime.now(), 1L);
        reserva.setCodigoReserva(codigoReserva);
        when(reservaRepository.findById(codigoReserva)).thenReturn(java.util.Optional.of(reserva));
        reservaService.deletarReserva(codigoReserva);
        verify(reservaRepository, times(1)).findById(codigoReserva);
        verify(reservaRepository, times(1)).delete(reserva);
    }

    private NovaReservaRequest fakeRequest(){
        return new NovaReservaRequest(
                Arrays.asList(1L, 2L),
                List.of(new ServicoRequest(1L, 1L)),
                List.of(new ItemRequest(1L, 1L)),
                1L,
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );
    }

    private QuartoResponse fakeQuartoResponse(){
        return new QuartoResponse(
                1L,
                TipoQuarto.LUXO_SIMPLES,
                "Quarto simples com vista para o jardim",
                new BanheiroResponse(1L, TipoBanheiro.LUXO, "Banheiro privativo com chuveiro"),
                "Hotel ABC",
                "Rua das Flores, 123",
                "Cidade A",
                2,
                new BigDecimal("100.00")
        );
    }

    private ClienteResponse fakeClienteResponse() {
        return new ClienteResponse(
                1L,
                "123456789",
                "John Doe",
                "email@teste.com"
        );
    }
}
