package br.com.fiap.quartos.msquarto.service;

import br.com.fiap.quartos.msquarto.domain.Localidade;
import br.com.fiap.quartos.msquarto.domain.Propriedade;
import br.com.fiap.quartos.msquarto.domain.Quarto;
import br.com.fiap.quartos.msquarto.exception.LocalidadeNaoEncontradaException;
import br.com.fiap.quartos.msquarto.exception.PropriedadeNaoEncontradaException;
import br.com.fiap.quartos.msquarto.exception.QuartoNaoEncontradoException;
import br.com.fiap.quartos.msquarto.repository.LocalidadeRepository;
import br.com.fiap.quartos.msquarto.repository.PropriedadeRepository;
import br.com.fiap.quartos.msquarto.repository.QuartoRepository;
import br.com.fiap.quartos.msquarto.request.PropriedadeRequest;
import br.com.fiap.quartos.msquarto.response.PropriedadeResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PropriedadeService {

    private static final Logger logger = LoggerFactory.getLogger(PropriedadeService.class);

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private LocalidadeRepository localidadeRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    public Page<PropriedadeResponse> getAllPropriedadesByLocalidade(Long localidadeId, Pageable pageable) throws LocalidadeNaoEncontradaException {
        logger.info("Obtendo todas as propriedades pela localidade id: {} com pageable: {}", localidadeId, pageable);

        Localidade localidade = localidadeRepository.findById(localidadeId).orElseThrow(LocalidadeNaoEncontradaException::new);

        Page<Propriedade> propriedades = propriedadeRepository.findByLocalidade(localidade, pageable);
        logger.info("Recuperadas {} propriedades", propriedades.getTotalElements());
        return propriedades.map(propriedade -> new PropriedadeResponse().toResponsePropriedade(propriedade));
    }

    public PropriedadeResponse getPropriedadeById(Long id) throws PropriedadeNaoEncontradaException {
        logger.info("Obtendo propriedade pelo ID: {}", id);
        Propriedade propriedade = propriedadeRepository.findById(id).orElseThrow(PropriedadeNaoEncontradaException::new);
        logger.info("Propriedade recuperada - ID: {}", id);
        return new PropriedadeResponse(propriedade);
    }

    public PropriedadeResponse createPropriedade(Long localidadeId, PropriedadeRequest propriedadeRequest) throws LocalidadeNaoEncontradaException {
        logger.info("Criando nova propriedade para a localidade com ID: {}", localidadeId);
        Localidade localidade = localidadeRepository.findById(localidadeId).orElseThrow(LocalidadeNaoEncontradaException::new);
        Propriedade propriedade = propriedadeRepository.save(new Propriedade(propriedadeRequest, localidade));
        logger.info("Propriedade criada - ID: {}", propriedade.getId());
        return new PropriedadeResponse().toResponsePropriedade(propriedade);
    }

    public void adicionarQuartoAPropriedade(@Valid Propriedade propriedade, @Valid Quarto quarto) {
        logger.info("Adicionando quarto com ID {} à propriedade com ID: {}", quarto.getId(), propriedade.getId());

        propriedade.addQuarto(quarto);
        propriedadeRepository.save(propriedade);

        logger.info("Quarto adicionado à propriedade com sucesso");
    }

    public PropriedadeResponse updatePropriedade(Long id, PropriedadeRequest propriedadeRequest) throws PropriedadeNaoEncontradaException, LocalidadeNaoEncontradaException {
        logger.info("Atualizando propriedade com ID {}: {}", id, propriedadeRequest);
        Propriedade propriedade = propriedadeRepository.findById(id).orElseThrow(PropriedadeNaoEncontradaException::new);

        if (propriedadeRequest.getIdLocalidade() != null) {
            Localidade localidade = localidadeRepository.findById(propriedadeRequest.getIdLocalidade()).orElseThrow(LocalidadeNaoEncontradaException::new);
            propriedade.setLocalidade(localidade);
            logger.info("Localidade da propriedade atualizada - Nova Localidade ID: {}", localidade.getId());
        }

        propriedade.atualizar(propriedadeRequest);
        propriedadeRepository.save(propriedade);

        logger.info("Propriedade atualizada - ID: {}", propriedade.getId());
        return new PropriedadeResponse().toResponsePropriedade(propriedade);
    }

    public void deletePropriedade(Long id) throws PropriedadeNaoEncontradaException {
        logger.info("Excluindo propriedade com ID: {}", id);
        propriedadeRepository.delete(propriedadeRepository.findById(id).orElseThrow(PropriedadeNaoEncontradaException::new));
        logger.info("Propriedade excluída - ID: {}", id);
    }
}
