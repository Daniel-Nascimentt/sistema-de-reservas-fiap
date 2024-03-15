package br.com.fiap.quartos.msquarto.service;

import br.com.fiap.quartos.msquarto.domain.Propriedade;
import br.com.fiap.quartos.msquarto.domain.Quarto;
import br.com.fiap.quartos.msquarto.domain.TipoBanheiro;
import br.com.fiap.quartos.msquarto.domain.TipoQuarto;
import br.com.fiap.quartos.msquarto.exception.PropriedadeNaoEncontradaException;
import br.com.fiap.quartos.msquarto.exception.QuartoNaoEncontradoException;
import br.com.fiap.quartos.msquarto.repository.PropriedadeRepository;
import br.com.fiap.quartos.msquarto.repository.QuartoProjection;
import br.com.fiap.quartos.msquarto.repository.QuartoRepository;
import br.com.fiap.quartos.msquarto.request.QuartoRequest;
import br.com.fiap.quartos.msquarto.response.BanheiroResponse;
import br.com.fiap.quartos.msquarto.response.PropriedadeResponse;
import br.com.fiap.quartos.msquarto.response.QuartoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuartoService {

    private static final Logger logger = LoggerFactory.getLogger(QuartoService.class);

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private PropriedadeService propriedadeService;

    public QuartoResponse getQuartoById(Long id) throws QuartoNaoEncontradoException {
        logger.info("Buscando quarto com ID: {}", id);
        return new QuartoResponse(quartoRepository.findById(id).orElseThrow(QuartoNaoEncontradoException::new));
    }

    @Transactional
    public QuartoResponse createQuarto(QuartoRequest quartoRequest) throws PropriedadeNaoEncontradaException {
        logger.info("Criando novo quarto");

        Propriedade propriedade = propriedadeRepository.findById(quartoRequest.getPropriedadeId()).orElseThrow(PropriedadeNaoEncontradaException::new);

        Quarto quarto = quartoRepository.save(quartoRequest.toDomain());
        quarto.associarAPropriedade(propriedade);

        propriedadeService.adicionarQuartoAPropriedade(propriedade, quarto);
        logger.info("Quarto criado com sucesso");

        return new QuartoResponse(quarto, new PropriedadeResponse(propriedade));
    }

    @Transactional
    public QuartoResponse updateQuarto(Long id, QuartoRequest quartoRequest) throws QuartoNaoEncontradoException, PropriedadeNaoEncontradaException {
        logger.info("Atualizando quarto com ID: {}", id);

        Quarto quarto = quartoRepository.findById(id).orElseThrow(QuartoNaoEncontradoException::new);
        Propriedade propriedade = propriedadeRepository.findById(quartoRequest.getPropriedadeId()).orElseThrow(PropriedadeNaoEncontradaException::new);

        quarto.atualizar(quartoRequest, propriedade);
        propriedadeService.adicionarQuartoAPropriedade(propriedade, quarto);

        logger.info("Quarto atualizado com sucesso");

        return new QuartoResponse(quartoRepository.save(quarto));
    }

    @Transactional
    public void deleteQuarto(Long idQuarto, Long idPropriedade) throws QuartoNaoEncontradoException, PropriedadeNaoEncontradaException {
        logger.info("Excluindo quarto com ID: {}", idQuarto);

        Quarto quarto = quartoRepository.findById(idQuarto).orElseThrow(QuartoNaoEncontradoException::new);
        Propriedade propriedade = propriedadeRepository.findById(idPropriedade).orElseThrow(PropriedadeNaoEncontradaException::new);

        logger.info("Removendo quarto da propriedade");
        propriedade.removerQuarto(quarto);

        logger.info("Excluindo quarto");
        quartoRepository.delete(quarto);

        logger.info("Salvando propriedade após a remoção do quarto");
        propriedadeRepository.save(propriedade);

        logger.info("Quarto excluído com sucesso");
    }

    public Page<QuartoResponse> getAllQuartosByLocalidade(Long localidadeId, Pageable pageable) {
        logger.info("Buscando todos os quartos por localidade com ID: {}", localidadeId);

        Page<QuartoProjection> quartos = quartoRepository.findAllQuartosByLocalidade(localidadeId, pageable);

        return quartos.map(quartoProjection -> {
            return new QuartoResponse(
                    quartoProjection.getQuartoId(),
                    TipoQuarto.valueOf(quartoProjection.getTipoQuarto()),
                    quartoProjection.getDescricaoQuarto(),
                    new BanheiroResponse(quartoProjection.getBanheiroId(), TipoBanheiro.valueOf(quartoProjection.getTipoBanheiro()), quartoProjection.getDescricaoBanheiro()),
                    quartoProjection.getNomePropriedade(),
                    quartoProjection.getEnderecoPropriedade(),
                    quartoProjection.getNomeLocalidade(),
                    quartoProjection.getTotalHospedes(),
                    quartoProjection.getValorDiaria()
            );
        });
    }

    public Page<QuartoResponse> obterQuartosPorListDeIds(List<Long> ids, Pageable pageable) {
        Page<Quarto> quartos = quartoRepository.findByListId(ids, pageable);
        return quartos.map(QuartoResponse::new);
    }
}
