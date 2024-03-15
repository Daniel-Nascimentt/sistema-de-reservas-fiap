package br.com.fiap.servicos.hospedagem.msservicosopcionais.service;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.domain.Item;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.exception.ItemNaoEncontradoException;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.repository.ItemRepository;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.request.ItemRequest;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.response.ItemResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private ItemRepository itemRepository;

    public ItemResponse criarItem(ItemRequest itemRequest) {
        logger.info("Criando um novo item...");
        Item item = itemRepository.save(itemRequest.toDomain());
        logger.info("Item criado com sucesso!");
        return new ItemResponse(item);
    }

    public ItemResponse obterItemPorId(Long id) throws ItemNaoEncontradoException {
        logger.info("Obtendo item pelo ID: {}", id);
        Item item = itemRepository.findById(id).orElseThrow(ItemNaoEncontradoException::new);
        logger.info("Item encontrado!");
        return new ItemResponse(item);
    }

    public Page<ItemResponse> listarItensPorPropriedade(Long id, Pageable pageable) {
        logger.info("Listando itens paginadamente...");
        Page<Item> itens = itemRepository.findByIdPropriedade(id, pageable);
        logger.info("Número total de itens encontrados: {}", itens.getTotalElements());
        return itens.map(ItemResponse::new);
    }

    public ItemResponse atualizarItem(Long id, ItemRequest itemRequest) throws ItemNaoEncontradoException {
        logger.info("Atualizando item com ID {}...", id);
        Item item = itemRepository.findById(id).orElseThrow(ItemNaoEncontradoException::new);
        item.atualizar(itemRequest);
        item = itemRepository.save(item);
        logger.info("Item atualizado com sucesso!!");
        return new ItemResponse(item);
    }

    public void deletarItem(Long id) throws ItemNaoEncontradoException {
        logger.info("Deletando item com ID: {}", id);
        Item item = itemRepository.findById(id).orElseThrow(ItemNaoEncontradoException::new);
        itemRepository.delete(item);
        logger.info("Item deletado com sucesso");
    }


    public Page<ItemResponse> obterItensPorListIds(List<Long> ids, Pageable pageable) {
        logger.info("Listando itens com ids {}...", ids);
        Page<Item> itens = itemRepository.findByListId(ids, pageable);
        logger.info("Número total de itens encontrados: {}", itens.getTotalElements());
        return itens.map(ItemResponse::new);
    }
}
