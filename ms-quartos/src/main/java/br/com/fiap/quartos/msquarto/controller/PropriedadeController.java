package br.com.fiap.quartos.msquarto.controller;

import br.com.fiap.quartos.msquarto.exception.LocalidadeNaoEncontradaException;
import br.com.fiap.quartos.msquarto.exception.PropriedadeNaoEncontradaException;
import br.com.fiap.quartos.msquarto.request.PropriedadeRequest;
import br.com.fiap.quartos.msquarto.response.PropriedadeResponse;
import br.com.fiap.quartos.msquarto.service.PropriedadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/propriedades")
public class PropriedadeController {

    @Autowired
    private PropriedadeService propriedadeService;

    @GetMapping("/listar/{localidadeId}")
    public ResponseEntity<Page<PropriedadeResponse>> getAllPropriedadesByLocalidade(@PathVariable Long localidadeId, Pageable pageable) throws LocalidadeNaoEncontradaException {
        return ResponseEntity.ok(propriedadeService.getAllPropriedadesByLocalidade(localidadeId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropriedadeResponse> getPropriedadeById(@PathVariable Long id) throws PropriedadeNaoEncontradaException {
        PropriedadeResponse propriedade = propriedadeService.getPropriedadeById(id);
        return ResponseEntity.ok(propriedade);
    }

    @PostMapping("/{localidadeId}")
    public ResponseEntity<PropriedadeResponse> createPropriedade(@PathVariable Long localidadeId, @Valid @RequestBody PropriedadeRequest propriedadeRequest) throws LocalidadeNaoEncontradaException {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(propriedadeService.createPropriedade(localidadeId, propriedadeRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropriedadeResponse> updatePropriedade(@PathVariable Long id, @Valid @RequestBody PropriedadeRequest propriedadeRequest) throws PropriedadeNaoEncontradaException, LocalidadeNaoEncontradaException {
        return ResponseEntity.ok(propriedadeService.updatePropriedade(id, propriedadeRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePropriedade(@PathVariable Long id) throws PropriedadeNaoEncontradaException {
        propriedadeService.deletePropriedade(id);
        return ResponseEntity.noContent().build();
    }
}
