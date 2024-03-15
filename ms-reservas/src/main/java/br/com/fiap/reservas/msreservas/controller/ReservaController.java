package br.com.fiap.reservas.msreservas.controller;

import br.com.fiap.reservas.msreservas.exception.*;
import br.com.fiap.reservas.msreservas.request.DisponibilidadeRequest;
import br.com.fiap.reservas.msreservas.request.NovaReservaRequest;
import br.com.fiap.reservas.msreservas.response.QuartoResponse;
import br.com.fiap.reservas.msreservas.response.ReservaResponse;
import br.com.fiap.reservas.msreservas.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping(value = "/dispinibilidades")
    public Page<QuartoResponse> getQuartosDisponiveis(@RequestBody @Valid DisponibilidadeRequest request){
        return reservaService.getQuartosDisponiveis(request.getIdLocalidade(), request.getQuantidadeHospedesParaReserva(), request.getCheckin(), request.getCheckout());
    }

    @PostMapping(value = "/preReservar")
    public ResponseEntity<ReservaResponse> preReserva(@RequestBody @Valid NovaReservaRequest request) throws QuartoJaReservadoException, DatacheckinInvalida {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.preReservar(request));
    }

    @PostMapping(value = "/reservar/{codigoReserva}/{idCliente}")
    public ResponseEntity<ReservaResponse> reservar(@PathVariable String codigoReserva, @PathVariable Long idCliente) throws ReservaNaoEncontradaException, ClienteInvalidoException {
        return ResponseEntity.ok(reservaService.reservar(UUID.fromString(codigoReserva), idCliente));
    }

    @GetMapping("/{codigoReserva}/{idCliente}")
    public ResponseEntity<ReservaResponse> buscarReservaPorCodigo(@PathVariable String codigoReserva, @PathVariable Long idCliente) throws ReservaNaoEncontradaException, ClienteInvalidoException {
        return ResponseEntity.ok(reservaService.buscarPorCodigo(UUID.fromString(codigoReserva), idCliente));
    }

    @PutMapping(value = "/{codigoReserva}")
    public ResponseEntity<ReservaResponse> atualizarReserva(@RequestBody @Valid NovaReservaRequest request, @PathVariable String codigoReserva) throws ReservaNaoEncontradaException, OperacaoReservaNaoPermitidaException, ClienteInvalidoException, DatacheckinInvalida {
        return ResponseEntity.ok(reservaService.atualizarReserva(request, UUID.fromString(codigoReserva)));
    }

    @DeleteMapping(value = "/{codigoReserva}")
    public ResponseEntity<ReservaResponse> deletarReserva(@PathVariable String codigoReserva) throws ReservaNaoEncontradaException, OperacaoReservaNaoPermitidaException {
        reservaService.deletarReserva(UUID.fromString(codigoReserva));
        return ResponseEntity.noContent().build();
    }

}
