package br.com.fiap.servicos.hospedagem.msservicosopcionais.service;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.domain.Servico;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.exception.ServicoNaoEncontradoException;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.repository.ServicoRepository;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.request.ServicoRequest;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.response.ServicoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class ServicoServiceTest {

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private ServicoService servicoService;

    @Test
    void testCriarServico() {
        ServicoRequest servicoRequest = new ServicoRequest("Serviço Teste", "Descrição Teste", BigDecimal.TEN, 1L);

        Mockito.when(servicoRepository.save(any(Servico.class))).thenReturn(fakeServico());

        ServicoResponse result = servicoService.criarServico(servicoRequest);

        assertNotNull(result);
        assertNotNull(result.getIdHotel());
        assertFalse(result.getNomeServico().isEmpty());
        assertFalse(result.getDescricaoServico().isEmpty());
        assertNotNull(result.getValorServico());
    }

    @Test
    void testObterServicoPorId() throws ServicoNaoEncontradoException {
        Long id = 1L;

        Mockito.when(servicoRepository.findById(id)).thenReturn(java.util.Optional.of(fakeServico()));

        ServicoResponse result = servicoService.obterServicoPorId(id);

        assertNotNull(result);
        assertNotNull(result.getIdHotel());
        assertFalse(result.getNomeServico().isEmpty());
        assertFalse(result.getDescricaoServico().isEmpty());
        assertNotNull(result.getValorServico());
    }

    @Test
    void testListarServicosPorHotel() {
        Page<Servico> servicosPage = new PageImpl<>(List.of(fakeServico(), fakeServico()));

        Mockito.when(servicoRepository.findByIdHotel(any(), any())).thenReturn(servicosPage);

        Page<ServicoResponse> result = servicoService.listarServicosPorHotel(1L, Pageable.unpaged());

        assertNotNull(result);
        assertTrue(result.getTotalElements() > 0);
    }

    @Test
    void testAtualizarServico() throws ServicoNaoEncontradoException {
        Long id = 1L;
        ServicoRequest servicoRequest = new ServicoRequest("Serviço Atualizado", "Descrição Atualizada", BigDecimal.valueOf(20), 1L);

        Servico servico = fakeServico();
        Mockito.when(servicoRepository.findById(id)).thenReturn(java.util.Optional.of(servico));
        Mockito.when(servicoRepository.save(any(Servico.class))).thenReturn(servico);

        assertNotEquals(servicoRequest.getNomeServico(), servico.getNomeServico());
        assertNotEquals(servicoRequest.getDescricaoServico(), servico.getDescricaoServico());

        ServicoResponse result = servicoService.atualizarServico(id, servicoRequest);

        assertNotNull(result);
        assertNotNull(result.getIdHotel());
        assertFalse(result.getNomeServico().isEmpty());
        assertFalse(result.getDescricaoServico().isEmpty());
        assertNotNull(result.getValorServico());
    }

    @Test
    void testDeletarServico() throws ServicoNaoEncontradoException {
        Long id = 1L;

        Servico servico = fakeServico();
        Mockito.when(servicoRepository.findById(id)).thenReturn(java.util.Optional.of(servico));

        assertDoesNotThrow(() -> servicoService.deletarServico(id));
    }

    @Test
    void testDeletarServicoThrowsServicoNaoEncontradoException() throws ServicoNaoEncontradoException {
        Long id = 1L;

        Mockito.when(servicoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ServicoNaoEncontradoException.class, () -> servicoService.deletarServico(id));
    }

    private Servico fakeServico(){
        return new Servico(
                "Almoço Executivo",
                "Refeição completa com opções de entrada, prato principal e sobremesa",
                new BigDecimal("35.90"),
                1L
        );
    }

}
