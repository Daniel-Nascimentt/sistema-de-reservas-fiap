package br.com.fiap.reservas.msreservas.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class DisponibilidadeRequest {

    @NotNull
    private Long idLocalidade;
    @NotNull
    private int quantidadeHospedesParaReserva;
    @NotNull
    private LocalDate checkin;
    @NotNull
    private LocalDate checkout;

}
