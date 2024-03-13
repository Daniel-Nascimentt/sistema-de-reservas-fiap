package br.com.fiap.quartos.msquarto.service;

import br.com.fiap.quartos.msquarto.domain.Propriedade;
import br.com.fiap.quartos.msquarto.domain.Quarto;
import br.com.fiap.quartos.msquarto.domain.TipoBanheiro;
import br.com.fiap.quartos.msquarto.domain.TipoQuarto;
import br.com.fiap.quartos.msquarto.exception.LocalidadeNaoEncontradaException;
import br.com.fiap.quartos.msquarto.exception.PropriedadeNaoEncontradaException;
import br.com.fiap.quartos.msquarto.exception.QuartoNaoEncontradoException;
import br.com.fiap.quartos.msquarto.repository.PropriedadeRepository;
import br.com.fiap.quartos.msquarto.repository.QuartoProjection;
import br.com.fiap.quartos.msquarto.repository.QuartoRepository;
import br.com.fiap.quartos.msquarto.request.QuartoRequest;
import br.com.fiap.quartos.msquarto.response.BanheiroResponse;
import br.com.fiap.quartos.msquarto.response.PropriedadeResponse;
import br.com.fiap.quartos.msquarto.response.QuartoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuartoService {

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private PropriedadeService propriedadeService;


    public QuartoResponse getQuartoById(Long id) throws QuartoNaoEncontradoException {
        return new QuartoResponse(quartoRepository.findById(id).orElseThrow(QuartoNaoEncontradoException::new));
    }

    public QuartoResponse createQuarto(QuartoRequest quartoRequest) throws PropriedadeNaoEncontradaException {

        Propriedade propriedade = propriedadeRepository.findById(quartoRequest.getPropriedadeId()).orElseThrow(PropriedadeNaoEncontradaException::new);

        Quarto quarto = quartoRepository.save(quartoRequest.toDomain());
        quarto.associarAPropriedade(propriedade);

        propriedadeService.adicionarQuartoAPropriedade(propriedade, quarto);
        return new QuartoResponse(quarto, new PropriedadeResponse(propriedade));
    }

    public QuartoResponse updateQuarto(Long id, QuartoRequest quartoRequest) throws QuartoNaoEncontradoException, PropriedadeNaoEncontradaException {
        Quarto quarto = quartoRepository.findById(id).orElseThrow(QuartoNaoEncontradoException::new);
        Propriedade propriedade = propriedadeRepository.findById(quartoRequest.getPropriedadeId()).orElseThrow(PropriedadeNaoEncontradaException::new);

        quarto.atualizar(quartoRequest, propriedade);
        propriedadeService.adicionarQuartoAPropriedade(propriedade, quarto);

        return new QuartoResponse(quartoRepository.save(quarto));
    }

    public void deleteQuarto(Long id) throws QuartoNaoEncontradoException {
        quartoRepository.delete(
                quartoRepository.findById(id).orElseThrow(QuartoNaoEncontradoException::new)
        );
    }

    public Page<QuartoResponse> getAllQuartosByLocalidade(Long localidadeId, Pageable pageable) throws LocalidadeNaoEncontradaException {
        Page<QuartoProjection> quartos = quartoRepository.findAllQuartosByLocalidade(localidadeId, pageable);

        return quartos.map(quartoProjection -> {
            return new QuartoResponse(
                    quartoProjection.getQuartoId(),
                    TipoQuarto.valueOf(quartoProjection.getTipoQuarto()),
                    quartoProjection.getDescricaoQuarto(),
                    new BanheiroResponse(quartoProjection.getBanheiroId(), TipoBanheiro.valueOf(quartoProjection.getTipoBanheiro()), quartoProjection.getDescricaoBanheiro()),
                    quartoProjection.getNomePropriedade(),
                    quartoProjection.getEnderecoPropriedade(),
                    quartoProjection.getNomeLocalidade()
            );
        });
    }
}
