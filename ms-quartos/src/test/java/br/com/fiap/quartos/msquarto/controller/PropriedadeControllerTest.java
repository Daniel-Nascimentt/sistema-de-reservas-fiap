package br.com.fiap.quartos.msquarto.controller;

import br.com.fiap.quartos.msquarto.request.PropriedadeRequest;
import br.com.fiap.quartos.msquarto.response.PropriedadeResponse;
import br.com.fiap.quartos.msquarto.response.QuartoResponse;
import br.com.fiap.quartos.msquarto.service.PropriedadeService;
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

@WebMvcTest(PropriedadeController.class)
class PropriedadeControllerTest {

    @MockBean
    private PropriedadeService propriedadeService;
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllPropriedadesByLocalidade() throws Exception {
        Long localidadeId = 1L;
        Page<PropriedadeResponse> propriedadePage = new PageImpl<>(List.of(fakeResponse(), fakeResponse()));
        when(propriedadeService.getAllPropriedadesByLocalidade(eq(localidadeId), any(Pageable.class))).thenReturn(propriedadePage);

        mockMvc.perform(get("/propriedades/listar/{localidadeId}", localidadeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }

    @Test
    void getPropriedadeById() throws Exception {
        Long propriedadeId = 1L;
        PropriedadeResponse propriedadeResponse = fakeResponse();
        when(propriedadeService.getPropriedadeById(propriedadeId)).thenReturn(propriedadeResponse);

        mockMvc.perform(get("/propriedades/{id}", propriedadeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPropriedade").exists())
                .andExpect(jsonPath("$.nomePropriedade").exists())
                .andExpect(jsonPath("$.descricaoAmenidades").exists())
                .andExpect(jsonPath("$.enderecoPropriedade").exists())
                .andExpect(jsonPath("$.localidade").exists());
    }

    @Test
    void createPropriedade() throws Exception {
        Long localidadeId = 1L;
        PropriedadeRequest propriedadeRequest = fakeRequest();
        PropriedadeResponse propriedadeResponse = fakeResponse();
        when(propriedadeService.createPropriedade(eq(localidadeId), any(PropriedadeRequest.class))).thenReturn(propriedadeResponse);

        mockMvc.perform(post("/propriedades/{localidadeId}", localidadeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propriedadeRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPropriedade").exists())
                .andExpect(jsonPath("$.nomePropriedade").exists())
                .andExpect(jsonPath("$.descricaoAmenidades").exists())
                .andExpect(jsonPath("$.enderecoPropriedade").exists())
                .andExpect(jsonPath("$.localidade").exists());
    }

    @Test
    void updatePropriedade() throws Exception {
        Long propriedadeId = 1L;
        PropriedadeRequest propriedadeRequest = fakeRequest();
        PropriedadeResponse propriedadeResponse = fakeResponse();
        when(propriedadeService.updatePropriedade(eq(propriedadeId), any(PropriedadeRequest.class))).thenReturn(propriedadeResponse);

        mockMvc.perform(put("/propriedades/{id}", propriedadeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propriedadeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPropriedade").exists())
                .andExpect(jsonPath("$.nomePropriedade").exists())
                .andExpect(jsonPath("$.descricaoAmenidades").exists())
                .andExpect(jsonPath("$.enderecoPropriedade").exists())
                .andExpect(jsonPath("$.localidade").exists());
    }

    @Test
    void deletePropriedade() throws Exception {
        Long propriedadeId = 1L;
        doNothing().when(propriedadeService).deletePropriedade(propriedadeId);

        mockMvc.perform(delete("/propriedades/{id}", propriedadeId))
                .andExpect(status().isNoContent());
    }

    private PropriedadeRequest fakeRequest(){
        return new PropriedadeRequest("Nome da propriedade", "Descricao das amenidades", "Rua endereço teste", 1L);
    }

    private PropriedadeResponse fakeResponse(){
        return new PropriedadeResponse(
                1L,
                "Nome da propriedade",
                "Descricao das amenidades",
                "Localizado em XPTO",
                "Rua endereço teste",
                List.of(new QuartoResponse())
        );
    }

}
