package br.com.fiap.reservas.msreservas.response;

import br.com.fiap.reservas.msreservas.domain.OpcionaisReserva;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@Schema
@AllArgsConstructor
public class OpcionaisReservaResponse {

    private String idOpcional;
    private BigDecimal valorDoOpcional;

    public OpcionaisReservaResponse (OpcionaisReserva opcionaisReserva){
       this.idOpcional = opcionaisReserva.getIdOpcional();
       this.valorDoOpcional = opcionaisReserva.getValor();
    }

}
