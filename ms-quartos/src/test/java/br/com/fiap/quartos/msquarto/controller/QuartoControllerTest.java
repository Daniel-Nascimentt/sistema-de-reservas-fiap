package br.com.fiap.quartos.msquarto.controller;

import br.com.fiap.quartos.msquarto.domain.TipoBanheiro;
import br.com.fiap.quartos.msquarto.domain.TipoQuarto;
import br.com.fiap.quartos.msquarto.request.BanheiroRequest;
import br.com.fiap.quartos.msquarto.request.QuartoRequest;
import br.com.fiap.quartos.msquarto.response.BanheiroResponse;
import br.com.fiap.quartos.msquarto.response.QuartoResponse;
import br.com.fiap.quartos.msquarto.service.QuartoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuartoController.class)
class QuartoControllerTest {

    @MockBean
    private QuartoService quartoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getQuartoById() throws Exception {
        Long quartoId = 1L;
        QuartoResponse quartoResponse = fakeResponse();
        when(quartoService.getQuartoById(quartoId)).thenReturn(quartoResponse);

        mockMvc.perform(get("/quartos/{id}", quartoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idQuarto").exists())
                .andExpect(jsonPath("$.tipoQuarto").exists())
                .andExpect(jsonPath("$.descricaoQuarto").exists())
                .andExpect(jsonPath("$.banheiroResponse").exists())
                .andExpect(jsonPath("$.nomePropriedade").exists())
                .andExpect(jsonPath("$.enderecoPropriedade").exists())
                .andExpect(jsonPath("$.localidadeQuarto").exists());
    }

    @Test
    void createQuarto() throws Exception {
        QuartoRequest quartoRequest = fakeRequest();
        QuartoResponse quartoResponse = fakeResponse();
        when(quartoService.createQuarto(any(QuartoRequest.class))).thenReturn(quartoResponse);

        mockMvc.perform(post("/quartos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quartoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idQuarto").exists())
                .andExpect(jsonPath("$.tipoQuarto").exists())
                .andExpect(jsonPath("$.descricaoQuarto").exists())
                .andExpect(jsonPath("$.banheiroResponse").exists())
                .andExpect(jsonPath("$.nomePropriedade").exists())
                .andExpect(jsonPath("$.enderecoPropriedade").exists())
                .andExpect(jsonPath("$.localidadeQuarto").exists());
    }

    @Test
    void updateQuarto() throws Exception {
        Long quartoId = 1L;
        QuartoRequest quartoRequest = fakeRequest();
        QuartoResponse quartoResponse = fakeResponse();
        when(quartoService.updateQuarto(eq(quartoId), any(QuartoRequest.class))).thenReturn(quartoResponse);

        mockMvc.perform(put("/quartos/{id}", quartoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quartoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idQuarto").exists())
                .andExpect(jsonPath("$.tipoQuarto").exists())
                .andExpect(jsonPath("$.descricaoQuarto").exists())
                .andExpect(jsonPath("$.banheiroResponse").exists())
                .andExpect(jsonPath("$.nomePropriedade").exists())
                .andExpect(jsonPath("$.enderecoPropriedade").exists())
                .andExpect(jsonPath("$.localidadeQuarto").exists());
    }

    @Test
    void deleteQuarto() throws Exception {
        Long quartoId = 1L;
        Long propriedadeId = 1L;
        doNothing().when(quartoService).deleteQuarto(quartoId, propriedadeId);

        mockMvc.perform(delete("/quartos/{quartoId}/{propriedadeId}", quartoId, propriedadeId))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllQuartosByLocalidade() throws Exception {
        Long localidadeId = 1L;
        Page<QuartoResponse> quartoPage = new PageImpl<>(List.of(fakeResponse(), fakeResponse()));
        when(quartoService.getAllQuartosByLocalidade(eq(localidadeId), any(Pageable.class))).thenReturn(quartoPage);

        mockMvc.perform(get("/quartos/localidade/{localidadeId}", localidadeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }

    private QuartoRequest fakeRequest(){
        return new QuartoRequest(
                TipoQuarto.LUXO_SIMPLES,
                "Descricao do quarto",
                new BanheiroRequest(TipoBanheiro.LUXO, "Descricao do banheiro"),
                1L);
    }

    private QuartoResponse fakeResponse(){
        return new QuartoResponse(
                1L,
                TipoQuarto.LUXO_DUPLO,
                "Descricao do quarto",
                new BanheiroResponse(1L, TipoBanheiro.LUXO, "Descricao banheiro"),
                "Nomeda propriedade",
                "Rua endereco propriedade teste",
                "Localidade do quarto XPTO"
        );
    }
}
