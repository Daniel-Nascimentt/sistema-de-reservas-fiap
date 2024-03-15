package br.com.fiap.reservas.msreservas.controller;

import br.com.fiap.reservas.msreservas.domain.TipoQuarto;
import br.com.fiap.reservas.msreservas.request.DisponibilidadeRequest;
import br.com.fiap.reservas.msreservas.request.ItemRequest;
import br.com.fiap.reservas.msreservas.request.NovaReservaRequest;
import br.com.fiap.reservas.msreservas.request.ServicoRequest;
import br.com.fiap.reservas.msreservas.response.BanheiroResponse;
import br.com.fiap.reservas.msreservas.response.OpcionaisReservaResponse;
import br.com.fiap.reservas.msreservas.response.QuartoResponse;
import br.com.fiap.reservas.msreservas.response.ReservaResponse;
import br.com.fiap.reservas.msreservas.service.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservaController.class)
class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService reservaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getQuartosDisponiveis() throws Exception {
        DisponibilidadeRequest request = fakeDisponibilidadeRequest();

        List<QuartoResponse> quartos = Arrays.asList(
                new QuartoResponse(1L, TipoQuarto.STANDARD_SIMPLES, "Quarto Standard", new BanheiroResponse(), "Hotel A", "Endereço A", "Cidade A", 2, BigDecimal.valueOf(100)),
                new QuartoResponse(2L, TipoQuarto.LUXO_SIMPLES, "Quarto Luxo", new BanheiroResponse(), "Hotel B", "Endereço B", "Cidade B", 2, BigDecimal.valueOf(200))
        );

        when(reservaService.getQuartosDisponiveis(request.getIdLocalidade(), request.getQuantidadeHospedesParaReserva(), request.getCheckin(), request.getCheckout())).thenReturn(new PageImpl<>(quartos));

        mockMvc.perform(get("/reservas/dispinibilidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));

        verify(reservaService, times(1)).getQuartosDisponiveis(request.getIdLocalidade(), request.getQuantidadeHospedesParaReserva(), request.getCheckin(), request.getCheckout());
    }

    @Test
    void preReserva() throws Exception {
        NovaReservaRequest request = fakeRequest();
        ReservaResponse response = fakeResponse();

        when(reservaService.preReservar(any())).thenReturn(response);

        mockMvc.perform(post("/reservas/preReservar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigoReserva").exists());

        verify(reservaService, times(1)).preReservar(any());
    }

    @Test
    void reservar() throws Exception {
        String codigoReserva = UUID.randomUUID().toString();
        Long idCliente = 1L;
        ReservaResponse response = fakeResponse();
        when(reservaService.reservar(UUID.fromString(codigoReserva), idCliente)).thenReturn(response);

        mockMvc.perform(post("/reservas/reservar/{codigoReserva}/{idCliente}", codigoReserva, idCliente))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoReserva").exists());

        verify(reservaService, times(1)).reservar(UUID.fromString(codigoReserva), idCliente);
    }

    @Test
    void buscarReservaPorCodigo() throws Exception {
        String codigoReserva = UUID.randomUUID().toString();
        Long idCliente = 1L;
        ReservaResponse response = fakeResponse();

        when(reservaService.buscarPorCodigo(UUID.fromString(codigoReserva), idCliente)).thenReturn(response);

        mockMvc.perform(get("/reservas/{codigoReserva}/{idCliente}", codigoReserva, idCliente))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoReserva").exists());

        verify(reservaService, times(1)).buscarPorCodigo(UUID.fromString(codigoReserva), idCliente);
    }

    @Test
    void atualizarReserva() throws Exception {
        String codigoReserva = UUID.randomUUID().toString();
        NovaReservaRequest request = fakeRequest();
        ReservaResponse response = fakeResponse();

        when(reservaService.atualizarReserva(any(), any())).thenReturn(response);

        mockMvc.perform(put("/reservas/{codigoReserva}", codigoReserva)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoReserva").exists());

        verify(reservaService, times(1)).atualizarReserva(any(), any());
    }

    @Test
    void deletarReserva() throws Exception {
        String codigoReserva = UUID.randomUUID().toString();

        mockMvc.perform(delete("/reservas/{codigoReserva}", codigoReserva))
                .andExpect(status().isNoContent());

        verify(reservaService, times(1)).deletarReserva(UUID.fromString(codigoReserva));
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

    private DisponibilidadeRequest fakeDisponibilidadeRequest() {
        return new DisponibilidadeRequest(
            1L,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );
    }

    private ReservaResponse fakeResponse(){
        return new ReservaResponse(
            UUID.randomUUID(),
                BigDecimal.valueOf(100),
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                List.of(new OpcionaisReservaResponse("1", BigDecimal.valueOf(10), 1L)),
                LocalDateTime.now(),
                1L
        );
    }
}
