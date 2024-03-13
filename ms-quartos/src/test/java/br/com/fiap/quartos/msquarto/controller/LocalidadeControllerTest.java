package br.com.fiap.quartos.msquarto.controller;

import br.com.fiap.quartos.msquarto.request.LocalidadeRequest;
import br.com.fiap.quartos.msquarto.response.LocalidadeResponse;
import br.com.fiap.quartos.msquarto.service.LocalidadeService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LocalidadeController.class)
class LocalidadeControllerTest {

    @MockBean
    private LocalidadeService localidadeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllLocalidades() throws Exception {
        Page<LocalidadeResponse> localidadePage = new PageImpl<>(List.of(fakeResponse(), fakeResponse()));
        when(localidadeService.getAllLocalidades(any(Pageable.class))).thenReturn(localidadePage);

        mockMvc.perform(get("/localidades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }

    @Test
    void getLocalidadeById() throws Exception {
        Long localidadeId = 1L;
        LocalidadeResponse localidadeResponse = fakeResponse();
        when(localidadeService.getLocalidadeById(localidadeId)).thenReturn(localidadeResponse);

        mockMvc.perform(get("/localidades/{id}", localidadeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idLocalidade").exists())
                .andExpect(jsonPath("$.nomeLocalidade").exists());
    }

    @Test
    void createLocalidade() throws Exception {
        LocalidadeRequest localidadeRequest = fakeRequest();
        LocalidadeResponse localidadeResponse = fakeResponse();
        when(localidadeService.createLocalidade(any(LocalidadeRequest.class))).thenReturn(localidadeResponse);

        mockMvc.perform(post("/localidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(localidadeRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idLocalidade").exists())
                .andExpect(jsonPath("$.nomeLocalidade").exists());
    }

    @Test
    void updateLocalidade() throws Exception {
        Long localidadeId = 1L;
        LocalidadeRequest localidadeRequest = fakeRequest();
        LocalidadeResponse localidadeResponse = fakeResponse();
        when(localidadeService.updateLocalidade(any(), any())).thenReturn(localidadeResponse);

        mockMvc.perform(put("/localidades/{id}", localidadeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(localidadeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idLocalidade").exists())
                .andExpect(jsonPath("$.nomeLocalidade").exists());
    }

    @Test
    void deleteLocalidade() throws Exception {
        Long localidadeId = 1L;
        doNothing().when(localidadeService).deleteLocalidade(localidadeId);

        mockMvc.perform(delete("/localidades/{id}", localidadeId))
                .andExpect(status().isNoContent());
    }

    private LocalidadeResponse fakeResponse(){
        return new LocalidadeResponse(1L, "Nome da localidade");
    }

    private LocalidadeRequest fakeRequest(){
        return new LocalidadeRequest("Nome da localidade");
    }

}
