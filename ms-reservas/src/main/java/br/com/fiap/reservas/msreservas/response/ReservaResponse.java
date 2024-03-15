package br.com.fiap.reservas.msreservas.response;

import br.com.fiap.reservas.msreservas.domain.Reserva;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Schema
public class ReservaResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID codigoReserva;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal valorTotalReserva;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate checkin;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate checkout;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OpcionaisReservaResponse> servicosOpcionais;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime dataPreReserva;

    public ReservaResponse(Reserva reserva) {
        this.codigoReserva = reserva.getCodigoReserva();
        this.valorTotalReserva = reserva.getValorTotalReserva();
        this.checkin = reserva.getCheckin();
        this.checkout = reserva.getCheckout();
        this.servicosOpcionais = reserva.getServicosOpcionais().stream().map(OpcionaisReservaResponse::new).collect(Collectors.toList());
        this.dataPreReserva = reserva.getDataPreReserva();
    }
}
