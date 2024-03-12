package br.com.fiap.servicos.hospedagem.msservicosopcionais.service;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.domain.Servico;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.exception.ServicoNaoEncontradoException;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.repository.ServicoRepository;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.request.ServicoRequest;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.response.ServicoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ServicoService {

    private static final Logger logger = LoggerFactory.getLogger(ServicoService.class);

    @Autowired
    private ServicoRepository servicoRepository;

    public ServicoResponse criarServico(ServicoRequest servicoRequest) {
        logger.info("Criando um novo serviço...");
        Servico servico = servicoRepository.save(servicoRequest.toDomain());
        logger.info("Serviço criado com sucesso!");
        return new ServicoResponse(servico);
    }

    public ServicoResponse obterServicoPorId(Long id) throws ServicoNaoEncontradoException {
        logger.info("Obtendo serviço pelo ID: {}", id);
        Servico servico = servicoRepository.findById(id).orElseThrow(ServicoNaoEncontradoException::new);
        logger.info("Serviço encontrado!");
        return new ServicoResponse(servico);
    }

    public Page<ServicoResponse> listarServicosPorHotel(Long id, Pageable pageable) {
        logger.info("Listando serviços paginadamente...");
        Page<Servico> servicos = servicoRepository.findByIdHotel(id, pageable);
        logger.info("Número total de serviços encontrados: {}", servicos.getTotalElements());
        return servicos.map(ServicoResponse::new);
    }

    public ServicoResponse atualizarServico(Long id, ServicoRequest servicoRequest) throws ServicoNaoEncontradoException {
        logger.info("Atualizando serviço com ID {}...", id);
        Servico servico = servicoRepository.findById(id).orElseThrow(ServicoNaoEncontradoException::new);
        servico.atualizar(servicoRequest);
        servico = servicoRepository.save(servico);
        logger.info("Serviço atualizado com sucesso!!");
        return new ServicoResponse(servico);
    }

    public void deletarServico(Long id) throws ServicoNaoEncontradoException {
        logger.info("Deletando serviço com ID: {}", id);
        Servico servico = servicoRepository.findById(id).orElseThrow(ServicoNaoEncontradoException::new);
        servicoRepository.delete(servico);
        logger.info("Serviço deletado com sucesso");
    }
}
