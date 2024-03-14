package br.com.fiap.reservas.msreservas.controller;

import br.com.fiap.reservas.msreservas.request.DisponibilidadeRequest;
import br.com.fiap.reservas.msreservas.response.QuartoResponse;
import br.com.fiap.reservas.msreservas.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public Page<QuartoResponse> getQuartosDisponiveis(@RequestBody @Valid DisponibilidadeRequest request){
        return reservaService.getQuartosDisponiveis(request.getIdLocalidade(), request.getQuantidadeHospedesParaReserva(), request.getCheckin(), request.getCheckout());
    }

}
