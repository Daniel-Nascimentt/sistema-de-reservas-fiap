package br.com.fiap.quartos.msquarto.controller;

import br.com.fiap.quartos.msquarto.exception.PropriedadeNaoEncontradaException;
import br.com.fiap.quartos.msquarto.exception.QuartoNaoEncontradoException;
import br.com.fiap.quartos.msquarto.request.QuartoRequest;
import br.com.fiap.quartos.msquarto.response.QuartoResponse;
import br.com.fiap.quartos.msquarto.service.QuartoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quartos")
public class QuartoController {

    @Autowired
    private QuartoService quartoService;

    @GetMapping("/{id}")
    public ResponseEntity<QuartoResponse> getQuartoById(@PathVariable Long id) throws QuartoNaoEncontradoException {
        return ResponseEntity.ok(quartoService.getQuartoById(id));
    }

    @PostMapping
    public ResponseEntity<QuartoResponse> createQuarto(@Valid @RequestBody QuartoRequest quartoRequest) throws PropriedadeNaoEncontradaException {
        return ResponseEntity.status(HttpStatus.CREATED).body(quartoService.createQuarto(quartoRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuartoResponse> updateQuarto(@PathVariable Long id, @Valid @RequestBody QuartoRequest quartoRequest) throws QuartoNaoEncontradoException, PropriedadeNaoEncontradaException {
        return ResponseEntity.ok(quartoService.updateQuarto(id, quartoRequest));
    }

    @DeleteMapping("/{idQuarto}/{idPropriedade}")
    public ResponseEntity<Void> deleteQuarto(@PathVariable Long idQuarto, @PathVariable Long idPropriedade) throws QuartoNaoEncontradoException, PropriedadeNaoEncontradaException {
        quartoService.deleteQuarto(idQuarto, idPropriedade);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/localidade/{localidadeId}")
    public ResponseEntity<Page<QuartoResponse>> getAllQuartosByLocalidade(
            @PathVariable Long localidadeId,
            Pageable pageable) {
        return ResponseEntity.ok(quartoService.getAllQuartosByLocalidade(localidadeId, pageable));
    }
}
