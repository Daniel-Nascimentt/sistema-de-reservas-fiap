package br.com.fiap.quartos.msquarto.service;

import br.com.fiap.quartos.msquarto.domain.Localidade;
import br.com.fiap.quartos.msquarto.exception.LocalidadeNaoEncontradaException;
import br.com.fiap.quartos.msquarto.repository.LocalidadeRepository;
import br.com.fiap.quartos.msquarto.request.LocalidadeRequest;
import br.com.fiap.quartos.msquarto.response.LocalidadeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LocalidadeService {

    private static final Logger logger = LoggerFactory.getLogger(LocalidadeService.class);

    @Autowired
    private LocalidadeRepository localidadeRepository;

    public Page<LocalidadeResponse> getAllLocalidades(Pageable pageable) {
        logger.info("Obtendo todas as localidades com paginadamente...");
        Page<Localidade> localidades = localidadeRepository.findAll(pageable);
        logger.info("Recuperadas {} localidades", localidades.getTotalElements());
        return localidades.map(LocalidadeResponse::new);
    }

    public LocalidadeResponse getLocalidadeById(Long id) throws LocalidadeNaoEncontradaException {
        logger.info("Obtendo localidade pelo ID: {}", id);
        Localidade localidade = localidadeRepository.findById(id).orElseThrow(LocalidadeNaoEncontradaException::new);
        logger.info("Localidade recuperada - ID: {}", id);
        return new LocalidadeResponse(localidade);
    }

    public LocalidadeResponse createLocalidade(LocalidadeRequest localidadeRequest) {
        logger.info("Criando nova localidade");
        Localidade localidade = localidadeRepository.save(localidadeRequest.toDomain());
        logger.info("Localidade criada - ID: {}", localidade.getId());
        return new LocalidadeResponse(localidade);
    }

    public LocalidadeResponse updateLocalidade(Long id, LocalidadeRequest localidadeRequest) throws LocalidadeNaoEncontradaException {
        logger.info("Atualizando localidade com ID: {}", id);
        Localidade localidade = localidadeRepository.findById(id).orElseThrow(LocalidadeNaoEncontradaException::new);
        localidade.atualizar(localidadeRequest);
        localidadeRepository.save(localidade);
        logger.info("Localidade atualizada - ID: {}", id);
        return new LocalidadeResponse(localidade);
    }

    public void deleteLocalidade(Long id) throws LocalidadeNaoEncontradaException {
        logger.info("Excluindo localidade com ID: {}", id);
        Localidade localidade = localidadeRepository.findById(id).orElseThrow(LocalidadeNaoEncontradaException::new);
        localidadeRepository.delete(localidade);
        logger.info("Localidade excluída - ID: {}", id);
    }
}
