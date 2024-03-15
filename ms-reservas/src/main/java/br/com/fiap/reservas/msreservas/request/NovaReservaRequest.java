package br.com.fiap.reservas.msreservas.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NovaReservaRequest {

    @NotNull
    private List<Long> idsQuarto = new ArrayList<>();
    private List<Long> idsServicos = new ArrayList<>();
    private List<Long> idsItens = new ArrayList<>();
    @NotNull
    private Long idCliente;
    @NotNull
    private LocalDate checkin;
    @NotNull
    private LocalDate checkout;

}
