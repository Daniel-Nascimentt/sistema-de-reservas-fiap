package br.com.fiap.servicos.hospedagem.msservicosopcionais.controller;


import br.com.fiap.servicos.hospedagem.msservicosopcionais.request.ServicoRequest;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.response.ServicoResponse;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.service.ServicoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ServicoController.class)
class ServicoControllerTest {

    @MockBean
    private ServicoService servicoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCriarServico() throws Exception {
        ServicoRequest servicoRequest = new ServicoRequest("Serviço Teste", "Descrição Teste", BigDecimal.TEN, 1L);
        ServicoResponse servicoResponse = fakeResponse();

        when(servicoService.criarServico(any(ServicoRequest.class))).thenReturn(servicoResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/servicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(servicoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeServico").value(servicoResponse.getNomeServico()))
                .andExpect(jsonPath("$.idPropriedade").isNotEmpty());
    }

    @Test
    void testObterServicoPorId() throws Exception {
        Long id = 1L;
        ServicoResponse servicoResponse = fakeResponse();

        when(servicoService.obterServicoPorId(anyLong())).thenReturn(servicoResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/servicos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeServico").value(servicoResponse.getNomeServico()))
                .andExpect(jsonPath("$.idPropriedade").isNotEmpty());
    }

    @Test
    void testListarServicos() throws Exception {
        Page<ServicoResponse> servicosPage = new PageImpl<>(Collections.emptyList());

        when(servicoService.listarServicosPorPropriedade(any(), any())).thenReturn(servicosPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/servicos/servicosPropriedade/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void testAtualizarServico() throws Exception {
        Long id = 1L;
        ServicoRequest servicoRequest = new ServicoRequest("Serviço Atualizado", "Descrição Atualizada", BigDecimal.valueOf(20), 1L);
        ServicoResponse servicoResponse = fakeResponse();

        when(servicoService.atualizarServico(anyLong(), any(ServicoRequest.class))).thenReturn(servicoResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/servicos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(servicoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeServico").value(servicoResponse.getNomeServico()))
                .andExpect(jsonPath("$.idPropriedade").isNotEmpty());
    }

    @Test
    void testDeletarServico() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/servicos/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void testListarServicosPorListIds() throws Exception {
        Page<ServicoResponse> servicosPage = new PageImpl<>(Collections.emptyList());

        when(servicoService.obterServicoPorListaIds(any(), any())).thenReturn(servicosPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/servicos/listarPorIds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(1L))))
                .andExpect(status().isOk());
    }

    private ServicoResponse fakeResponse() {
        return new ServicoResponse(
                1L,
                "Almoço Executivo",
                "Refeição completa com opções de entrada, prato principal e sobremesa",
                new BigDecimal("35.90"),
                1L
        );
    }
}