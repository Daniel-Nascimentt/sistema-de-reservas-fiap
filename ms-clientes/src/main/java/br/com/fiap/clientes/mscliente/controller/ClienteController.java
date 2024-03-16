package br.com.fiap.clientes.mscliente.controller;

import br.com.fiap.clientes.mscliente.exception.ClienteNaoEncontradoException;
import br.com.fiap.clientes.mscliente.exception.NumeroDePassaporteNaoInformadoException;
import br.com.fiap.clientes.mscliente.request.ClienteRequest;
import br.com.fiap.clientes.mscliente.response.ClienteResponse;
import br.com.fiap.clientes.mscliente.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private  ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obterClientePorId(@PathVariable Long id) throws ClienteNaoEncontradoException {
        return ResponseEntity.ok(clienteService.obterClientePorId(id));
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrarCliente(@RequestBody @Valid ClienteRequest request) throws NumeroDePassaporteNaoInformadoException {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(clienteService.cadastrarCliente(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizarCliente(@PathVariable Long id, @RequestBody @Valid ClienteRequest request) throws ClienteNaoEncontradoException, NumeroDePassaporteNaoInformadoException {
        return ResponseEntity.ok(clienteService.atualizarCliente(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) throws ClienteNaoEncontradoException {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}