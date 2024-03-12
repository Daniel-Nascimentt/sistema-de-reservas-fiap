package br.com.fiap.clientes.mscliente.controller;

import br.com.fiap.clientes.mscliente.request.ClienteRequest;
import br.com.fiap.clientes.mscliente.response.ClienteResponse;
import br.com.fiap.clientes.mscliente.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
        ClienteResponse clienteResponse = new ClienteResponse(/* preencha com dados apropriados */);

        Mockito.when(clienteService.obterClientePorId(clientId)).thenReturn(clienteResponse);

        mockMvc.perform(get("/clientes/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(clienteResponse.getNomeCompleto()))
                .andExpect(jsonPath("$.email").value(clienteResponse.getEmail()));
    }

    @Test
    void cadastrarCliente_deveRetornarClienteCriado() throws Exception {
        ClienteRequest clienteRequest = new ClienteRequest(/* preencha com dados apropriados */);
        ClienteResponse clienteResponse = new ClienteResponse(/* preencha com dados apropriados */);

        Mockito.when(clienteService.cadastrarCliente(clienteRequest)).thenReturn(clienteResponse);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(clienteResponse.getNomeCompleto()))
                .andExpect(jsonPath("$.email").value(clienteResponse.getEmail()));
    }

    @Test
    void atualizarCliente_deveRetornarClienteAtualizado() throws Exception {
        Long clientId = 1L;
        ClienteRequest clienteRequest = new ClienteRequest(/* preencha com dados apropriados */);
        ClienteResponse clienteResponse = new ClienteResponse(/* preencha com dados apropriados */);

        Mockito.when(clienteService.atualizarCliente(clientId, clienteRequest)).thenReturn(clienteResponse);

        mockMvc.perform(put("/clientes/{id}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(clienteResponse.getNomeCompleto()))
                .andExpect(jsonPath("$.email").value(clienteResponse.getEmail()));
    }

    @Test
    void deletarCliente_deveRetornarStatusNotFound() throws Exception {
        Long clientId = 1L;
        Mockito.doNothing().when(clienteService).deletarCliente(clientId);

        mockMvc.perform(delete("/clientes/{id}", clientId))
                .andExpect(status().isNotFound());
    }
}
