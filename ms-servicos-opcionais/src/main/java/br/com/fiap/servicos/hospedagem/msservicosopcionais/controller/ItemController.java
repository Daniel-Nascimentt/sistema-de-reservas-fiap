package br.com.fiap.servicos.hospedagem.msservicosopcionais.controller;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.exception.ItemNaoEncontradoException;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.request.ItemRequest;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.response.ItemResponse;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itens")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponse> criarItem(@Valid @RequestBody ItemRequest itemRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.criarItem(itemRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> obterItemPorId(@PathVariable Long id) throws ItemNaoEncontradoException {
        return ResponseEntity.ok(itemService.obterItemPorId(id));
    }

    @GetMapping("/itensHotel/{id}")
    public ResponseEntity<Page<ItemResponse>> listarItensPorHotel(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(itemService.listarItensPorHotel(id, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> atualizarItem(@PathVariable Long id, @Valid @RequestBody ItemRequest itemRequest) throws ItemNaoEncontradoException {
        return ResponseEntity.ok(itemService.atualizarItem(id, itemRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) throws ItemNaoEncontradoException {
        itemService.deletarItem(id);
        return ResponseEntity.noContent().build();
    }
}
