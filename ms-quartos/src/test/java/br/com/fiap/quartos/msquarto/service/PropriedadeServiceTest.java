package br.com.fiap.quartos.msquarto.service;

import br.com.fiap.quartos.msquarto.domain.Localidade;
import br.com.fiap.quartos.msquarto.domain.Propriedade;
import br.com.fiap.quartos.msquarto.domain.Quarto;
import br.com.fiap.quartos.msquarto.exception.LocalidadeNaoEncontradaException;
import br.com.fiap.quartos.msquarto.exception.PropriedadeNaoEncontradaException;
import br.com.fiap.quartos.msquarto.repository.LocalidadeRepository;
import br.com.fiap.quartos.msquarto.repository.PropriedadeRepository;
import br.com.fiap.quartos.msquarto.repository.QuartoRepository;
import br.com.fiap.quartos.msquarto.request.PropriedadeRequest;
import br.com.fiap.quartos.msquarto.response.PropriedadeResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PropriedadeServiceTest {

    @Mock
    private PropriedadeRepository propriedadeRepository;

    @Mock
    private LocalidadeRepository localidadeRepository;

    @Mock
    private QuartoRepository quartoRepository;

    @InjectMocks
    private PropriedadeService propriedadeService;

    @Test
    void getAllPropriedadesByLocalidade() throws LocalidadeNaoEncontradaException {
        Long localidadeId = 1L;
        Pageable pageable = Pageable.unpaged();
        Localidade localidade = fakeLocalidade();
        Page<Propriedade> propriedadePage = new PageImpl<>(List.of(fakePropriedade(), fakePropriedade()));

        when(localidadeRepository.findById(localidadeId)).thenReturn(Optional.of(localidade));
        when(propriedadeRepository.findByLocalidade(localidade, pageable)).thenReturn(propriedadePage);

        Page<PropriedadeResponse> result = propriedadeService.getAllPropriedadesByLocalidade(localidadeId, pageable);

        assertNotNull(result);
        assertTrue(result.getTotalElements() > 0);
    }

    @Test
    void getAllPropriedadesByLocalidade_LocalidadeNaoEncontrada() {
        Long localidadeId = 1L;
        Pageable pageable = Pageable.unpaged();

        when(localidadeRepository.findById(localidadeId)).thenReturn(Optional.empty());

        assertThrows(LocalidadeNaoEncontradaException.class, () -> propriedadeService.getAllPropriedadesByLocalidade(localidadeId, pageable));
    }

    @Test
    void getPropriedadeById() throws PropriedadeNaoEncontradaException {
        Long propriedadeId = 1L;
        Propriedade propriedade = fakePropriedade();
        when(propriedadeRepository.findById(propriedadeId)).thenReturn(Optional.of(propriedade));

        PropriedadeResponse result = propriedadeService.getPropriedadeById(propriedadeId);

        assertNotNull(result);
        assertFalse(result.getNomePropriedade().isEmpty());
        assertFalse(result.getEnderecoPropriedade().isEmpty());
        assertFalse(result.getDescricaoAmenidades().isEmpty());
    }

    @Test
    void getPropriedadeById_PropriedadeNaoEncontrada() {
        Long propriedadeId = 1L;
        when(propriedadeRepository.findById(propriedadeId)).thenReturn(Optional.empty());

        assertThrows(PropriedadeNaoEncontradaException.class, () -> propriedadeService.getPropriedadeById(propriedadeId));
    }

    @Test
    void createPropriedade() throws LocalidadeNaoEncontradaException {
        Long localidadeId = 1L;
        PropriedadeRequest propriedadeRequest = fakeRequest();
        Localidade localidade = new Localidade();
        Propriedade propriedade = new Propriedade(propriedadeRequest, localidade);

        when(localidadeRepository.findById(localidadeId)).thenReturn(Optional.of(localidade));
        when(propriedadeRepository.save(any(Propriedade.class))).thenReturn(propriedade);

        PropriedadeResponse result = propriedadeService.createPropriedade(localidadeId, propriedadeRequest);

        assertNotNull(result);
        assertFalse(result.getNomePropriedade().isEmpty());
        assertFalse(result.getEnderecoPropriedade().isEmpty());
        assertFalse(result.getDescricaoAmenidades().isEmpty());
    }

    @Test
    void createPropriedade_LocalidadeNaoEncontrada() {
        Long localidadeId = 1L;
        PropriedadeRequest propriedadeRequest = fakeRequest();

        when(localidadeRepository.findById(localidadeId)).thenReturn(Optional.empty());

        assertThrows(LocalidadeNaoEncontradaException.class, () -> propriedadeService.createPropriedade(localidadeId, propriedadeRequest));
    }

    @Test
    void adicionarQuartoAPropriedade() {
        Propriedade propriedade = fakePropriedade();
        Quarto quarto = new Quarto();

        assertDoesNotThrow(() -> propriedadeService.adicionarQuartoAPropriedade(propriedade, quarto));
    }

    @Test
    void updatePropriedade() throws PropriedadeNaoEncontradaException, LocalidadeNaoEncontradaException {
        Long propriedadeId = 1L;
        PropriedadeRequest propriedadeRequest = fakeRequest();
        Localidade localidade = new Localidade();
        Propriedade propriedade = fakePropriedade();

        when(propriedadeRepository.findById(propriedadeId)).thenReturn(Optional.of(propriedade));
        when(localidadeRepository.findById(anyLong())).thenReturn(Optional.of(localidade));
        when(propriedadeRepository.save(any(Propriedade.class))).thenReturn(propriedade);

        PropriedadeResponse result = propriedadeService.updatePropriedade(propriedadeId, propriedadeRequest);

        assertNotNull(result);
        assertFalse(result.getNomePropriedade().isEmpty());
        assertFalse(result.getEnderecoPropriedade().isEmpty());
        assertFalse(result.getDescricaoAmenidades().isEmpty());
    }

    @Test
    void updatePropriedade_PropriedadeNaoEncontrada() {
        Long propriedadeId = 1L;
        PropriedadeRequest propriedadeRequest = fakeRequest();

        when(propriedadeRepository.findById(propriedadeId)).thenReturn(Optional.empty());

        assertThrows(PropriedadeNaoEncontradaException.class, () -> propriedadeService.updatePropriedade(propriedadeId, propriedadeRequest));
    }

    @Test
    void updatePropriedade_LocalidadeNaoEncontrada() {
        Long propriedadeId = 1L;
        PropriedadeRequest propriedadeRequest = fakeRequest();

        when(propriedadeRepository.findById(propriedadeId)).thenReturn(Optional.of(fakePropriedade()));
        when(localidadeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(LocalidadeNaoEncontradaException.class, () -> propriedadeService.updatePropriedade(propriedadeId, propriedadeRequest));
    }

    @Test
    void deletePropriedade() throws PropriedadeNaoEncontradaException {
        Long propriedadeId = 1L;
        Propriedade propriedade = fakePropriedade();

        when(propriedadeRepository.findById(propriedadeId)).thenReturn(Optional.of(propriedade));

        assertDoesNotThrow(() -> propriedadeService.deletePropriedade(propriedadeId));
    }

    @Test
    void deletePropriedade_PropriedadeNaoEncontrada() {
        Long propriedadeId = 1L;

        when(propriedadeRepository.findById(propriedadeId)).thenReturn(Optional.empty());

        assertThrows(PropriedadeNaoEncontradaException.class, () -> propriedadeService.deletePropriedade(propriedadeId));
    }

    private Propriedade fakePropriedade(){
        return new Propriedade(fakeRequest(), fakeLocalidade());
    }

    private PropriedadeRequest fakeRequest(){
        return new PropriedadeRequest("Nome propriedade", "Descricao das amenidades", "Endereco propriedade", 1L);
    }

    private Localidade fakeLocalidade(){
        return new Localidade("Nome da localidade");
    }
    
}