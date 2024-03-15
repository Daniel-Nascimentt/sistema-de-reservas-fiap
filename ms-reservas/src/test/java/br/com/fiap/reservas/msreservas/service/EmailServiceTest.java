package br.com.fiap.reservas.msreservas.service;

import br.com.fiap.reservas.msreservas.client.MsEmailClient;
import br.com.fiap.reservas.msreservas.client.MsServicosClient;
import br.com.fiap.reservas.msreservas.domain.TipoQuarto;
import br.com.fiap.reservas.msreservas.response.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @Mock
    private MsServicosClient msServicosClient;

    @Mock
    private MsEmailClient msEmailClient;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void enviarEmail_Success() {

        when(msServicosClient.obterItensPorListDeIds(any(), any())).thenReturn(new PageImpl<>(List.of(new ItemResponse(1L, "NomeItem", BigDecimal.TEN, 1L, 1L))));
        when(msServicosClient.obterServicoPorListaIds(any(), any())).thenReturn(new PageImpl<>(List.of(new ServicoResponse(1L, "NomeServico", BigDecimal.TEN, 1L, 1L))));
        ReservaResponse reserva = new ReservaResponse();
        reserva.setCodigoReserva(UUID.randomUUID());
        reserva.setCheckin(LocalDate.now());
        reserva.setCheckout(LocalDate.now().plusDays(1));
        reserva.setValorTotalReserva(BigDecimal.TEN);
        OpcionaisReservaResponse opcionaisReservaResponse = new OpcionaisReservaResponse("S_1", BigDecimal.TEN, 1L);

        reserva.setServicosOpcionais(List.of(opcionaisReservaResponse));

        QuartoResponse quarto = new QuartoResponse();
        quarto.setTipoQuarto(TipoQuarto.LUXO_SIMPLES);
        quarto.setDescricaoQuarto("Quarto Luxo Simples");
        quarto.setBanheiroResponse(new BanheiroResponse());
        reserva.setQuartosDaReserva(Collections.singletonList(quarto));

        emailService.enviarEmail(reserva, "cliente@teste.com");

        verify(msEmailClient).enviarEmail(any());
    }



}
