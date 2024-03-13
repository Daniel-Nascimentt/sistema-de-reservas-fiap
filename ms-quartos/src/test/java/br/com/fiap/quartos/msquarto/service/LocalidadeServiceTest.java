package br.com.fiap.quartos.msquarto.service;

import br.com.fiap.quartos.msquarto.domain.Localidade;
import br.com.fiap.quartos.msquarto.domain.Propriedade;
import br.com.fiap.quartos.msquarto.exception.DelecaoNaoPermitidaException;
import br.com.fiap.quartos.msquarto.exception.LocalidadeNaoEncontradaException;
import br.com.fiap.quartos.msquarto.repository.LocalidadeRepository;
import br.com.fiap.quartos.msquarto.request.LocalidadeRequest;
import br.com.fiap.quartos.msquarto.request.PropriedadeRequest;
import br.com.fiap.quartos.msquarto.response.LocalidadeResponse;
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
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class LocalidadeServiceTest {

    @Mock
    private LocalidadeRepository localidadeRepository;

    @InjectMocks
    private LocalidadeService localidadeService;

    @Test
    void getAllLocalidades() {
        Pageable pageable = Pageable.unpaged();
        Page<Localidade> localidadePage = new PageImpl<>(List.of(fakeLocalidade(), fakeLocalidade()));
        when(localidadeRepository.findAll(pageable)).thenReturn(localidadePage);

        Page<LocalidadeResponse> result = localidadeService.getAllLocalidades(pageable);

        assertNotNull(result);
        assertTrue(result.getTotalElements() > 0);
    }

    @Test
    void getLocalidadeById() throws LocalidadeNaoEncontradaException {
        Long localidadeId = 1L;
        Localidade localidade = fakeLocalidade();
        when(localidadeRepository.findById(localidadeId)).thenReturn(Optional.of(localidade));

        LocalidadeResponse result = localidadeService.getLocalidadeById(localidadeId);

        assertNotNull(result);
        assertInstanceOf(LocalidadeResponse.class, result);
        assertFalse(result.getNomeLocalidade().isEmpty());
    }

    @Test
    void getLocalidadeById_LocalidadeNaoEncontrada() {
        Long localidadeId = 1L;
        when(localidadeRepository.findById(localidadeId)).thenReturn(Optional.empty());

        assertThrows(LocalidadeNaoEncontradaException.class, () -> localidadeService.getLocalidadeById(localidadeId));
    }

    @Test
    void createLocalidade() {
        LocalidadeRequest localidadeRequest = fakeRequest();
        Localidade localidade = new Localidade("Localidade atualizada");
        when(localidadeRepository.save(any(Localidade.class))).thenReturn(localidade);

        assertNotEquals(localidadeRequest.getNomeLocalidade(), localidade.getNomeLocalidade());

        LocalidadeResponse result = localidadeService.createLocalidade(localidadeRequest);

        assertNotNull(result);
        assertInstanceOf(LocalidadeResponse.class, result);
        assertFalse(result.getNomeLocalidade().isEmpty());
    }

    @Test
    void updateLocalidade() throws LocalidadeNaoEncontradaException {
        Long localidadeId = 1L;
        LocalidadeRequest localidadeRequest = fakeRequest();
        Localidade localidade = fakeLocalidade();
        when(localidadeRepository.findById(localidadeId)).thenReturn(Optional.of(localidade));
        when(localidadeRepository.save(any(Localidade.class))).thenReturn(localidade);

        LocalidadeResponse result = localidadeService.updateLocalidade(localidadeId, localidadeRequest);

        assertNotNull(result);
        assertInstanceOf(LocalidadeResponse.class, result);
        assertFalse(result.getNomeLocalidade().isEmpty());
    }

    @Test
    void updateLocalidade_LocalidadeNaoEncontrada() {
        Long localidadeId = 1L;
        LocalidadeRequest localidadeRequest = new LocalidadeRequest();
        when(localidadeRepository.findById(localidadeId)).thenReturn(Optional.empty());

        assertThrows(LocalidadeNaoEncontradaException.class, () -> localidadeService.updateLocalidade(localidadeId, localidadeRequest));
    }

    @Test
    void deleteLocalidade() throws LocalidadeNaoEncontradaException {
        Long localidadeId = 1L;
        Localidade localidade = new Localidade();
        when(localidadeRepository.findById(localidadeId)).thenReturn(Optional.of(localidade));

        assertDoesNotThrow(() -> localidadeService.deleteLocalidade(localidadeId));
    }

    @Test
    void deleteLocalidadeThrowsDelecaoNaoPermitidaException() throws DelecaoNaoPermitidaException {
        Long localidadeId = 1L;
        Localidade localidade = fakeLocalidade();
        localidade.getPropriedades().add(new Propriedade(new PropriedadeRequest("Nome propriedade", "Descricao das amenidades", "Endereco propriedade", 1L), fakeLocalidade()));
        when(localidadeRepository.findById(localidadeId)).thenReturn(Optional.of(localidade));

        assertThrows(DelecaoNaoPermitidaException.class, () -> localidadeService.deleteLocalidade(localidadeId));
    }

    @Test
    void deleteLocalidade_LocalidadeNaoEncontrada() {
        Long localidadeId = 1L;
        when(localidadeRepository.findById(localidadeId)).thenReturn(Optional.empty());

        assertThrows(LocalidadeNaoEncontradaException.class, () -> localidadeService.deleteLocalidade(localidadeId));
    }

    private LocalidadeResponse fakeResponse(){
        return new LocalidadeResponse(1L, "Nome da localidade");
    }

    private LocalidadeRequest fakeRequest(){
        return new LocalidadeRequest("Nome da localidade");
    }

    private Localidade fakeLocalidade(){
        return new Localidade("Nome da localidade");
    }
}
