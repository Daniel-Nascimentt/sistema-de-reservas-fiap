package br.com.fiap.clientes.mscliente.service;

import br.com.fiap.clientes.mscliente.domain.Cliente;
import br.com.fiap.clientes.mscliente.exception.ClienteNaoEncontradoException;
import br.com.fiap.clientes.mscliente.exception.NumeroDePassaporteNaoInformadoException;
import br.com.fiap.clientes.mscliente.repository.ClienteRepository;
import br.com.fiap.clientes.mscliente.request.ClienteRequest;
import br.com.fiap.clientes.mscliente.response.ClienteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteResponse obterClientePorId(Long id) throws ClienteNaoEncontradoException {
        logger.info("Obtendo cliente por ID: {}", id);

        return new ClienteResponse(clienteRepository.findById(id).orElseThrow(() -> {
            logger.warn("Cliente não encontrado com ID: {}", id);
            return new ClienteNaoEncontradoException();
        }));
    }

    public ClienteResponse cadastrarCliente(ClienteRequest clienteRequest) throws NumeroDePassaporteNaoInformadoException {
        logger.info("Cadastrando novo cliente...");
        return new ClienteResponse(clienteRepository.save(clienteRequest.toDomain()));
    }

    public ClienteResponse atualizarCliente(Long id, ClienteRequest clienteRequest) throws ClienteNaoEncontradoException, NumeroDePassaporteNaoInformadoException {
        logger.info("Atualizando cliente com ID: {}", id);

        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> {
            logger.warn("Cliente não encontrado com ID: {}", id);
            return new ClienteNaoEncontradoException();
        });

        cliente.atualizarCampos(clienteRequest);

        return new ClienteResponse(clienteRepository.save(cliente));
    }

    public void deletarCliente(Long id) throws ClienteNaoEncontradoException {
        logger.info("Deletando cliente com ID: {}", id);

        clienteRepository.delete(clienteRepository.findById(id).orElseThrow(() -> {
            logger.warn("Cliente não encontrado com ID: {}", id);
            return new ClienteNaoEncontradoException();
        }));
    }
}



