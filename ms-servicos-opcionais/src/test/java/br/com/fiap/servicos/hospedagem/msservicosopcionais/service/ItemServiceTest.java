package br.com.fiap.servicos.hospedagem.msservicosopcionais.service;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.domain.Item;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.exception.ItemNaoEncontradoException;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.repository.ItemRepository;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.request.ItemRequest;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.response.ItemResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    void testCriarItem() {
        ItemRequest itemRequest = new ItemRequest("Item Teste", "Descrição Teste", BigDecimal.TEN, 1L);
        Item itemMock = fakeItem();
        when(itemRepository.save(any(Item.class))).thenReturn(itemMock);

        ItemResponse result = itemService.criarItem(itemRequest);

        assertNotNull(result);
        assertNotNull(result.getIdPropriedade());
        assertFalse(result.getNomeItem().isEmpty());
        assertFalse(result.getDescricaoItem().isEmpty());
        assertNotNull(result.getValorItem());
    }

    @Test
    void testObterItemPorId() throws ItemNaoEncontradoException {
        Long id = 1L;
        Item itemMock = fakeItem();
        when(itemRepository.findById(id)).thenReturn(Optional.of(itemMock));

        ItemResponse result = itemService.obterItemPorId(id);

        assertNotNull(result);
        assertNotNull(result.getIdPropriedade());
        assertFalse(result.getNomeItem().isEmpty());
        assertFalse(result.getDescricaoItem().isEmpty());
        assertNotNull(result.getValorItem());
    }

    @Test
    void testListarItensPorPropriedade() {
        Pageable pageable = Pageable.unpaged();
        Page<Item> itensMock = new PageImpl<>(List.of(fakeItem(), fakeItem()));
        when(itemRepository.findByIdPropriedade(any(), any())).thenReturn(itensMock);

        Page<ItemResponse> result = itemService.listarItensPorPropriedade(1L, pageable);

        assertNotNull(result);
        assertTrue(result.getTotalElements() > 0);

    }

    @Test
    void testAtualizarItem() throws ItemNaoEncontradoException {
        Long id = 1L;
        ItemRequest itemRequest = new ItemRequest("Item Atualizado", "Descrição Atualizada", BigDecimal.valueOf(20), 1L);
        Item itemMock = fakeItem();
        when(itemRepository.findById(id)).thenReturn(Optional.of(itemMock));
        when(itemRepository.save(any(Item.class))).thenReturn(itemMock);

        assertNotEquals(itemRequest.getNomeItem(), itemMock.getNomeItem());
        assertNotEquals(itemRequest.getDescricaoItem(), itemMock.getDescricaoItem());

        ItemResponse result = itemService.atualizarItem(id, itemRequest);

        assertNotNull(result);
        assertNotNull(result.getIdPropriedade());
        assertFalse(result.getNomeItem().isEmpty());
        assertFalse(result.getDescricaoItem().isEmpty());
        assertNotNull(result.getValorItem());
    }

    @Test
    void testDeletarItem() throws ItemNaoEncontradoException {
        Long id = 1L;
        Item itemMock = fakeItem();
        when(itemRepository.findById(id)).thenReturn(Optional.of(itemMock));

        assertDoesNotThrow(() -> itemService.deletarItem(id));

    }

    @Test
    void testListarItensPorListIds() {
        Pageable pageable = Pageable.unpaged();
        Page<Item> itensMock = new PageImpl<>(List.of(fakeItem(), fakeItem()));
        when(itemRepository.findByListId(any(), any())).thenReturn(itensMock);

        Page<ItemResponse> result = itemService.obterItensPorListIds(List.of(1L, 2L), pageable);

        assertNotNull(result);
        assertTrue(result.getTotalElements() > 0);

    }

    @Test
    void testDeletarItemThrowsItemNaoEncontradoException() throws ItemNaoEncontradoException {
        Long id = 1L;
        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ItemNaoEncontradoException.class, () -> itemService.deletarItem(id));
    }

    private Item fakeItem(){
        return new Item(
                "Refrigerante",
                "Bebida gelada, diversas opções de sabores",
                new BigDecimal("4.50"),
                1L
        );
    }

}
