package br.com.fiap.quartos.msquarto.service;

import br.com.fiap.quartos.msquarto.domain.*;
import br.com.fiap.quartos.msquarto.exception.PropriedadeNaoEncontradaException;
import br.com.fiap.quartos.msquarto.exception.QuartoNaoEncontradoException;
import br.com.fiap.quartos.msquarto.repository.PropriedadeRepository;
import br.com.fiap.quartos.msquarto.repository.QuartoProjection;
import br.com.fiap.quartos.msquarto.repository.QuartoRepository;
import br.com.fiap.quartos.msquarto.request.BanheiroRequest;
import br.com.fiap.quartos.msquarto.request.PropriedadeRequest;
import br.com.fiap.quartos.msquarto.request.QuartoRequest;
import br.com.fiap.quartos.msquarto.response.BanheiroResponse;
import br.com.fiap.quartos.msquarto.response.QuartoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class QuartoServiceTest {

    @Mock
    private QuartoRepository quartoRepository;

    @Mock
    private PropriedadeRepository propriedadeRepository;

    @Mock
    private PropriedadeService propriedadeService;

    @InjectMocks
    private QuartoService quartoService;

    @Test
    void getQuartoById() throws QuartoNaoEncontradoException {
        Long quartoId = 1L;
        Quarto quarto = fakeQuarto();
        when(quartoRepository.findById(quartoId)).thenReturn(Optional.of(quarto));

        QuartoResponse result = quartoService.getQuartoById(quartoId);

        assertNotNull(result);
        assertNotNull(result.getBanheiroResponse());
        assertNotNull(result.getTipoQuarto());
        assertFalse(result.getDescricaoQuarto().isEmpty());
        assertNull(result.getLocalidadeQuarto());
        assertNull(result.getNomePropriedade());
        assertNull(result.getEnderecoPropriedade());
        assertNull(result.getNomePropriedade());

    }

    @Test
    void getQuartoById_QuartoNaoEncontrado() {
        Long quartoId = 1L;
        when(quartoRepository.findById(quartoId)).thenReturn(Optional.empty());

        assertThrows(QuartoNaoEncontradoException.class, () -> quartoService.getQuartoById(quartoId));
    }

    @Test
    void createQuarto() throws PropriedadeNaoEncontradaException {
        QuartoRequest quartoRequest = fakeRequest();
        Propriedade propriedade = fakePropriedade();
        Quarto quarto = quartoRequest.toDomain();

        when(propriedadeRepository.findById(anyLong())).thenReturn(Optional.of(propriedade));
        when(quartoRepository.save(any(Quarto.class))).thenReturn(quarto);

        QuartoResponse result = quartoService.createQuarto(quartoRequest);

        verify(propriedadeService, times(1)).adicionarQuartoAPropriedade(any(), any());

        assertNotNull(result);
        assertNotNull(result.getBanheiroResponse());
        assertFalse(result.getNomePropriedade().isEmpty());
        assertNotNull(result.getTipoQuarto());
        assertFalse(result.getLocalidadeQuarto().isEmpty());
        assertFalse(result.getNomePropriedade().isEmpty());
        assertFalse(result.getEnderecoPropriedade().isEmpty());
    }

    @Test
    void createQuarto_PropriedadeNaoEncontrada() {
        QuartoRequest quartoRequest = fakeRequest();

        when(propriedadeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PropriedadeNaoEncontradaException.class, () -> quartoService.createQuarto(quartoRequest));
    }

    @Test
    void updateQuarto() throws QuartoNaoEncontradoException, PropriedadeNaoEncontradaException {
        Long quartoId = 1L;
        QuartoRequest quartoRequest = fakeRequest();
        Quarto quarto = fakeQuarto();
        Propriedade propriedade = fakePropriedade();

        when(quartoRepository.findById(quartoId)).thenReturn(Optional.of(quarto));
        when(propriedadeRepository.findById(anyLong())).thenReturn(Optional.of(propriedade));
        when(quartoRepository.save(any(Quarto.class))).thenReturn(quarto);

        QuartoResponse result = quartoService.updateQuarto(quartoId, quartoRequest);

        verify(propriedadeService, times(1)).adicionarQuartoAPropriedade(any(), any());

        assertNotNull(result);
        assertNotNull(result.getBanheiroResponse());
        assertNotNull(result.getTipoQuarto());
        assertFalse(result.getDescricaoQuarto().isEmpty());
        assertNull(result.getLocalidadeQuarto());
        assertNull(result.getNomePropriedade());
        assertNull(result.getEnderecoPropriedade());
        assertNull(result.getNomePropriedade());
    }

    @Test
    void updateQuarto_QuartoNaoEncontrado() {
        Long quartoId = 1L;
        QuartoRequest quartoRequest = fakeRequest();

        when(quartoRepository.findById(quartoId)).thenReturn(Optional.empty());

        assertThrows(QuartoNaoEncontradoException.class, () -> quartoService.updateQuarto(quartoId, quartoRequest));
    }

    @Test
    void updateQuarto_PropriedadeNaoEncontrada() {
        Long quartoId = 1L;
        QuartoRequest quartoRequest = fakeRequest();

        when(quartoRepository.findById(quartoId)).thenReturn(Optional.of(new Quarto()));
        when(propriedadeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PropriedadeNaoEncontradaException.class, () -> quartoService.updateQuarto(quartoId, quartoRequest));
    }

    @Test
    void deleteQuarto() throws QuartoNaoEncontradoException {
        Long quartoId = 1L;
        Long propriedadeId = 1L;
        Quarto quarto = fakeQuarto();

        when(propriedadeRepository.findById(any())).thenReturn(Optional.of(fakePropriedade()));
        when(quartoRepository.findById(quartoId)).thenReturn(Optional.of(quarto));

        assertDoesNotThrow(() -> quartoService.deleteQuarto(quartoId, propriedadeId));
    }

    @Test
    void deleteQuarto_QuartoNaoEncontrado() {
        Long quartoId = 1L;
        Long propriedadeId = 1L;

        when(quartoRepository.findById(quartoId)).thenReturn(Optional.empty());

        assertThrows(QuartoNaoEncontradoException.class, () -> quartoService.deleteQuarto(quartoId, propriedadeId));
    }

    @Test
    void getAllQuartosByLocalidade() {
        Long localidadeId = 1L;
        Pageable pageable = Pageable.unpaged();
        Page<QuartoProjection> quartos = new PageImpl<>(Collections.emptyList());

        when(quartoRepository.findAllQuartosByLocalidade(localidadeId, pageable)).thenReturn(quartos);

        Page<QuartoResponse> result = quartoService.getAllQuartosByLocalidade(localidadeId, pageable);

        assertNotNull(result);
        assertEquals(quartos.map(q -> new QuartoResponse(q.getQuartoId(), TipoQuarto.valueOf(q.getTipoQuarto()), q.getDescricaoQuarto(),
                new BanheiroResponse(q.getBanheiroId(), TipoBanheiro.valueOf(q.getTipoBanheiro()), q.getDescricaoBanheiro()),
                q.getNomePropriedade(), q.getEnderecoPropriedade(), q.getNomeLocalidade(), q.getTotalHospedes(), q.getValorDiaria())), result);
    }


    private Propriedade fakePropriedade(){
        return new Propriedade(
                new PropriedadeRequest("Nome propriedade", "Descricao das amenidades", "Endereco propriedade", 1L),
                new Localidade("Nome da localidade"));
    }

    private QuartoRequest fakeRequest(){
        return new QuartoRequest(
                TipoQuarto.LUXO_DUPLO,
                "Descricao do quarto",
                new BanheiroRequest(TipoBanheiro.LUXO, "Descricao do banheiro"),
                1L,
                2,
                BigDecimal.TEN
        );
    }

    private Quarto fakeQuarto(){
        return fakeRequest().toDomain();
    }


}
