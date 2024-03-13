package br.com.fiap.quartos.msquarto.service;

import br.com.fiap.quartos.msquarto.domain.Localidade;
import br.com.fiap.quartos.msquarto.exception.LocalidadeNaoEncontradaException;
import br.com.fiap.quartos.msquarto.repository.LocalidadeRepository;
import br.com.fiap.quartos.msquarto.request.LocalidadeRequest;
import br.com.fiap.quartos.msquarto.response.LocalidadeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LocalidadeService {

    @Autowired
    private LocalidadeRepository localidadeRepository;

    public Page<LocalidadeResponse> getAllLocalidades(Pageable pageable) {
        Page<Localidade> localidades = localidadeRepository.findAll(pageable);
        return localidades.map(LocalidadeResponse::new);
    }

    public LocalidadeResponse getLocalidadeById(Long id) throws LocalidadeNaoEncontradaException {
        return new LocalidadeResponse(localidadeRepository.findById(id).orElseThrow(LocalidadeNaoEncontradaException::new));
    }

    public LocalidadeResponse createLocalidade(LocalidadeRequest localidadeRequest) {
        return new LocalidadeResponse(
                localidadeRepository.save(localidadeRequest.toDomain())
        );
    }

    public LocalidadeResponse updateLocalidade(Long id, LocalidadeRequest localidadeRequest) throws LocalidadeNaoEncontradaException {
        Localidade localidade = localidadeRepository.findById(id).orElseThrow(LocalidadeNaoEncontradaException::new);
        localidade.atualizar(localidadeRequest);
        localidadeRepository.save(localidade);
        return new LocalidadeResponse(localidade);
    }

    public void deleteLocalidade(Long id) throws LocalidadeNaoEncontradaException {
        localidadeRepository.delete(
                localidadeRepository.findById(id).orElseThrow(LocalidadeNaoEncontradaException::new)
        );
    }
}
