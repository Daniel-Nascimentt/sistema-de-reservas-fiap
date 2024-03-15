package br.com.fiap.servicos.hospedagem.msservicosopcionais.controller;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.request.ItemRequest;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.response.ItemResponse;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.service.ItemService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @MockBean
    private ItemService itemService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCriarItem() throws Exception {
        ItemRequest itemRequest = new ItemRequest("Item Teste", "Descrição Teste", BigDecimal.TEN, 1L);
        ItemResponse itemResponse = fakeResponse();

        when(itemService.criarItem(any(ItemRequest.class))).thenReturn(itemResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/itens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeItem").value(itemResponse.getNomeItem()))
                .andExpect(jsonPath("$.idPropriedade").isNotEmpty());
    }

    @Test
    void testObterItemPorId() throws Exception {
        Long id = 1L;
        ItemResponse itemResponse = fakeResponse();

        when(itemService.obterItemPorId(anyLong())).thenReturn(itemResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/itens/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeItem").value(itemResponse.getNomeItem()))
                .andExpect(jsonPath("$.idPropriedade").isNotEmpty());
    }

    @Test
    void testListarItensPorPropriedade() throws Exception {
        Page<ItemResponse> itensResponse = new PageImpl<>(List.of(fakeResponse()));

        when(itemService.listarItensPorPropriedade(any(), any())).thenReturn(itensResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/itens/itensPropriedade/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testAtualizarItem() throws Exception {
        Long id = 1L;
        ItemRequest itemRequest = new ItemRequest("Item Atualizado", "Descrição Atualizada", BigDecimal.valueOf(20), 1L);
        ItemResponse itemResponse = fakeResponse();

        when(itemService.atualizarItem(anyLong(), any(ItemRequest.class))).thenReturn(itemResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/itens/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeItem").value(itemResponse.getNomeItem()))
                .andExpect(jsonPath("$.idPropriedade").isNotEmpty());
    }

    @Test
    void testDeletarItem() throws Exception {
        Long id = 1L;

       mockMvc.perform(MockMvcRequestBuilders.delete("/itens/{id}", id))
                .andExpect(status().isNoContent());

    }

    @Test
    void testListarItensPorListIds() throws Exception {
        Page<ItemResponse> itensResponse = new PageImpl<>(List.of(fakeResponse()));

        when(itemService.obterItensPorListIds(any(), any())).thenReturn(itensResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/itens/listarPorIds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(1L))))
                .andExpect(status().isOk());
    }

    private ItemResponse fakeResponse() {
        return new ItemResponse(
                1L,
                "Refrigerante",
                "Bebida gelada, diversas opções de sabores",
                new BigDecimal("4.50"),
                1L
        );
    }

}

