package br.com.fiap.clientes.mscliente.controller;

import br.com.fiap.clientes.mscliente.domain.Cliente;
import br.com.fiap.clientes.mscliente.domain.Endereco;
import br.com.fiap.clientes.mscliente.request.ClienteRequest;
import br.com.fiap.clientes.mscliente.request.EnderecoRequest;
import br.com.fiap.clientes.mscliente.response.ClienteResponse;
import br.com.fiap.clientes.mscliente.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    @Test
    void obterClientePorId_deveRetornarClienteExistente() throws Exception {
        Long clientId = 1L;
        ClienteResponse clienteResponse = fakeResponse();

        when(clienteService.obterClientePorId(clientId)).thenReturn(clienteResponse);

        mockMvc.perform(get("/clientes/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeCompleto").value(clienteResponse.getNomeCompleto()))
                .andExpect(jsonPath("$.email").value(clienteResponse.getEmail()));
    }

    @Test
    void cadastrarCliente_deveRetornarClienteCriado() throws Exception {
        ClienteRequest clienteRequest = fakeRequest();
        ClienteResponse clienteResponse = fakeResponse();

        when(clienteService.cadastrarCliente(any())).thenReturn(fakeResponse());

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeCompleto").value(clienteResponse.getNomeCompleto()))
                .andExpect(jsonPath("$.email").value(clienteResponse.getEmail()));
    }

    @Test
    void atualizarCliente_deveRetornarClienteAtualizado() throws Exception {
        Long clientId = 1L;
        ClienteRequest clienteRequest = fakeRequest();
        ClienteResponse clienteResponse = fakeResponse();

        when(clienteService.atualizarCliente(any(), any())).thenReturn(clienteResponse);

        mockMvc.perform(put("/clientes/{id}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeCompleto").value(clienteResponse.getNomeCompleto()))
                .andExpect(jsonPath("$.email").value(clienteResponse.getEmail()));
    }

    @Test
    void deletarCliente_deveRetornarStatusNotFound() throws Exception {
        Long clientId = 1L;
        doNothing().when(clienteService).deletarCliente(clientId);

        mockMvc.perform(delete("/clientes/{id}", clientId))
                .andExpect(status().isNoContent());
    }

    private ClienteResponse fakeResponse(){
            Endereco endereco = new Endereco("12345-678", "Rua das Flores", 42, "SP");

            return new ClienteResponse(
                    new Cliente(
                    "Brasil",
                    "15333329087",
                    "AB123456",
                    true,
                    "Joao Silva",
                    LocalDate.of(1990, 1, 1),
                    endereco,
                    "123456789",
                    "joao.silva@example.com"
            ));
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
                "15333329087",
                null,
                false,
                "Joao Silva",
                LocalDate.of(1990, 1, 1),
                endereco,
                "123456789",
                "joao.silva@example.com"
        );
    }
    }

