package br.com.fiap.servicos.hospedagem.msservicosopcionais.controller;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.exception.ServicoNaoEncontradoException;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.request.ServicoRequest;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.response.ServicoResponse;
import br.com.fiap.servicos.hospedagem.msservicosopcionais.service.ServicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @PostMapping
    public ResponseEntity<ServicoResponse> criarServico(@Valid @RequestBody ServicoRequest servicoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoService.criarServico(servicoRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponse> obterServicoPorId(@PathVariable Long id) throws ServicoNaoEncontradoException {
        return ResponseEntity.ok(servicoService.obterServicoPorId(id));
    }

    @GetMapping("/servicosPropriedade/{id}")
    public ResponseEntity<Page<ServicoResponse>> listarServicosPorPropriedade(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(servicoService.listarServicosPorPropriedade(id, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponse> atualizarServico(@PathVariable Long id, @Valid @RequestBody ServicoRequest servicoRequest) throws ServicoNaoEncontradoException {
        return ResponseEntity.ok(servicoService.atualizarServico(id, servicoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long id) throws ServicoNaoEncontradoException {
        servicoService.deletarServico(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/listarPorIds")
    public Page<ServicoResponse> obterServicoPorListaIds(@RequestBody List<Long> ids, Pageable pageable) throws ServicoNaoEncontradoException {
        return servicoService.obterServicoPorListaIds(ids, pageable);
    }

}