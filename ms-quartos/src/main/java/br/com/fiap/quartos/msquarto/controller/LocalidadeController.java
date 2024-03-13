package br.com.fiap.quartos.msquarto.controller;

import br.com.fiap.quartos.msquarto.exception.LocalidadeNaoEncontradaException;
import br.com.fiap.quartos.msquarto.request.LocalidadeRequest;
import br.com.fiap.quartos.msquarto.response.LocalidadeResponse;
import br.com.fiap.quartos.msquarto.service.LocalidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/localidades")
public class LocalidadeController {

    @Autowired
    private LocalidadeService localidadeService;

    @GetMapping
    public ResponseEntity<Page<LocalidadeResponse>> getAllLocalidades(Pageable pageable) {
        return ResponseEntity.ok(localidadeService.getAllLocalidades(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalidadeResponse> getLocalidadeById(@PathVariable Long id) throws LocalidadeNaoEncontradaException {
        return ResponseEntity.ok(localidadeService.getLocalidadeById(id));
    }

    @PostMapping
    public ResponseEntity<LocalidadeResponse> createLocalidade(@Valid @RequestBody LocalidadeRequest localidadeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(localidadeService.createLocalidade(localidadeRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocalidadeResponse> updateLocalidade(@PathVariable Long id,
                                                               @Valid @RequestBody LocalidadeRequest localidadeRequest) throws LocalidadeNaoEncontradaException {
        return ResponseEntity.ok(localidadeService.updateLocalidade(id, localidadeRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocalidade(@PathVariable Long id) throws LocalidadeNaoEncontradaException {
        localidadeService.deleteLocalidade(id);
        return ResponseEntity.noContent().build();
    }

}


