package br.com.fiap.reservas.msreservas.service;

import br.com.fiap.reservas.msreservas.client.MsQuartosClient;
import br.com.fiap.reservas.msreservas.client.MsServicosClient;
import br.com.fiap.reservas.msreservas.domain.Reserva;
import br.com.fiap.reservas.msreservas.domain.TipoBanheiro;
import br.com.fiap.reservas.msreservas.domain.TipoQuarto;
import br.com.fiap.reservas.msreservas.exception.QuartoJaReservadoException;
import br.com.fiap.reservas.msreservas.repository.ReservaQuartoRepository;
import br.com.fiap.reservas.msreservas.repository.ReservaRepository;
import br.com.fiap.reservas.msreservas.request.NovaReservaRequest;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

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
    }

    @Test
    public void testPreReservar() throws QuartoJaReservadoException {
        NovaReservaRequest request = fakeRequest();

        List<QuartoResponse> quartos = Arrays.asList(
                fakeQuartoResponse(),
                fakeQuartoResponse()
        );

        List<ServicoResponse> servicos = Arrays.asList(
                new ServicoResponse(1L, "Serviço 1", "Descricao 1", BigDecimal.valueOf(50), 1L),
                new ServicoResponse(2L, "Serviço 2", "Descricao 2", BigDecimal.valueOf(75), 2L)
        );

        List<ItemResponse> itens = Arrays.asList(
                new ItemResponse(1L, "Item 1", "Descricao 1", BigDecimal.valueOf(20), 1L),
                new ItemResponse(2L, "Item 2", "Descricao 2", BigDecimal.valueOf(30), 2L)
        );

        when(msQuartosClient.obterQuartosPorListIds(request.getIdsQuarto(), Pageable.unpaged())).thenReturn(new PageImpl<>(quartos));
        when(reservaQuartoRepository.findQuartosDisponiveisBetweenCheckinAndCheckout(request.getCheckin(), request.getCheckout())).thenReturn(new ArrayList<>());
        when(msServicosClient.obterServicoPorListaIds(request.getIdsServicos(), Pageable.unpaged())).thenReturn(new PageImpl<>(servicos));
        when(msServicosClient.obterItensPorListDeIds(request.getIdsItens(), Pageable.unpaged())).thenReturn(new PageImpl<>(itens));
        when(reservaRepository.save(any())).thenReturn(new Reserva());

        ReservaResponse result = reservaService.preReservar(request);

        assertNotNull(result);
    }

    @Test
    public void testPreReservarQuartoJaReservadoException() {
        NovaReservaRequest request = fakeRequest();

        when(reservaQuartoRepository.findQuartosDisponiveisBetweenCheckinAndCheckout(request.getCheckin(), request.getCheckout()))
                .thenReturn(Arrays.asList(1L));

        assertThrows(QuartoJaReservadoException.class, () -> reservaService.preReservar(request));
    }


    private NovaReservaRequest fakeRequest(){
        return new NovaReservaRequest(
                Arrays.asList(1L, 2L),
                Arrays.asList(1L, 2L),
                Arrays.asList(1L, 2L),
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
}
