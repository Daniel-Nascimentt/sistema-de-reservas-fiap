package br.com.fiap.clientes.mscliente.service;

import br.com.fiap.clientes.mscliente.domain.Cliente;
import br.com.fiap.clientes.mscliente.domain.Endereco;
import br.com.fiap.clientes.mscliente.exception.ClienteNaoEncontradoException;
import br.com.fiap.clientes.mscliente.exception.NumeroDePassaporteNaoInformadoException;
import br.com.fiap.clientes.mscliente.repository.ClienteRepository;
import br.com.fiap.clientes.mscliente.request.ClienteRequest;
import br.com.fiap.clientes.mscliente.request.EnderecoRequest;
import br.com.fiap.clientes.mscliente.response.ClienteResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void obterClientePorId_ClienteExistente_DeveRetornarClienteResponse() throws ClienteNaoEncontradoException {
        Long idClienteExistente = 1L;
        Cliente clienteExistente = fakeCliente();
        when(clienteRepository.findById(idClienteExistente)).thenReturn(Optional.of(clienteExistente));

        ClienteResponse clienteResponse = clienteService.obterClientePorId(idClienteExistente);

        assertNotNull(clienteResponse);
        assertEquals(clienteExistente.getCpf(), clienteResponse.getCpf());
        assertEquals(clienteExistente.getNomeCompleto(), clienteResponse.getNomeCompleto());
        assertEquals(clienteExistente.getEmail(), clienteResponse.getEmail());
    }

    @Test
    void obterClientePorId_ClienteNaoExistente_DeveLancarException() {
        Long idClienteNaoExistente = 2L;
        when(clienteRepository.findById(idClienteNaoExistente)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.obterClientePorId(idClienteNaoExistente));
    }

    @Test
    void cadastrarCliente_DeveRetornarClienteResponse() throws NumeroDePassaporteNaoInformadoException {
        ClienteRequest clienteRequest = fakeRequest();
        when(clienteRepository.save(any())).thenReturn(fakeCliente());

        ClienteResponse clienteResponse = clienteService.cadastrarCliente(clienteRequest);

        assertNotNull(clienteResponse);
    }

    @Test
    void cadastrarCliente_DeveRetornarExceptionNumeroDePassaporteNaoInformado() throws NumeroDePassaporteNaoInformadoException {

        EnderecoRequest endereco = new EnderecoRequest(
                "12345-678",
                "Rua das Flores",
                42,
                "SP"
        );

        ClienteRequest clienteRequest = new ClienteRequest(
                "Brasil",
                "098765431",
                null,
                true,
                "João Silva Atualizado",
                LocalDate.of(1990, 1, 1),
                endereco,
                "123456789",
                "joao.silva2@example.com"
        );

        assertThrows(NumeroDePassaporteNaoInformadoException.class, () -> clienteService.cadastrarCliente(clienteRequest));
    }

    @Test
    void atualizarCliente_ClienteExistente_DeveRetornarClienteResponse() throws ClienteNaoEncontradoException, NumeroDePassaporteNaoInformadoException {
        Long idClienteExistente = 1L;
        ClienteRequest clienteRequest = fakeRequest();
        Cliente clienteExistente = fakeCliente();
        when(clienteRepository.findById(any())).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.save(any())).thenReturn(clienteExistente);

        assertNotEquals(clienteExistente.getCpf(), clienteRequest.getCpf());
        assertNotEquals(clienteExistente.getNomeCompleto(), clienteRequest.getNomeCompleto());
        assertNotEquals(clienteExistente.getEmail(), clienteRequest.getEmail());

        ClienteResponse clienteResponse = clienteService.atualizarCliente(idClienteExistente, clienteRequest);

        assertNotNull(clienteResponse);
        assertEquals(clienteExistente.getCpf(), clienteResponse.getCpf());
        assertEquals(clienteExistente.getNomeCompleto(), clienteResponse.getNomeCompleto());
        assertEquals(clienteExistente.getEmail(), clienteResponse.getEmail());
    }

    @Test
    void atualizarCliente_ClienteNaoExistente_DeveLancarException() {
        Long idClienteNaoExistente = 2L;
        ClienteRequest clienteRequest = fakeRequest();
        when(clienteRepository.findById(idClienteNaoExistente)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.atualizarCliente(idClienteNaoExistente, clienteRequest));
    }

    @Test
    void deletarCliente_ClienteExistente_DeveDeletarCliente() throws ClienteNaoEncontradoException {
        Long idClienteExistente = 1L;
        Cliente clienteExistente = fakeCliente();
        when(clienteRepository.findById(idClienteExistente)).thenReturn(Optional.of(clienteExistente));

        clienteService.deletarCliente(idClienteExistente);

        verify(clienteRepository, times(1)).delete(clienteExistente); // Verifica se o método delete foi chamado uma vez
    }

    @Test
    void deletarCliente_ClienteNaoExistente_DeveLancarException() {
        Long idClienteNaoExistente = 2L;
        when(clienteRepository.findById(idClienteNaoExistente)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.deletarCliente(idClienteNaoExistente));
    }


    private Cliente fakeCliente(){
        Endereco endereco = new Endereco("12345-678", "Rua das Flores", 42, "SP");

        return new Cliente(
                "Brasil",
                "1234567890",
                null,
                false,
                "João Silva",
                LocalDate.of(1990, 1, 1),
                endereco,
                "123456789",
                "joao.silva@example.com"
        );
    }

   private ClienteRequest fakeRequest(){
       EnderecoRequest endereco = new EnderecoRequest(
               "12345-678",
               "Rua das Flores",
               42,
               "SP"
       );

       return new ClienteRequest(
               "Brasil",
               "098765431",
               null,
               false,
               "João Silva Atualizado",
               LocalDate.of(1990, 1, 1),
               endereco,
               "123456789",
               "joao.silva2@example.com"
       );
   }

}
