package br.com.fiap.reservas.msreservas.request;

import br.com.fiap.reservas.msreservas.exception.DataCheckinInvalidaException;
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
    private List<ServicoRequest> servicos = new ArrayList<>();
    private List<ItemRequest> itens = new ArrayList<>();
    @NotNull
    private Long idCliente;
    @NotNull
    private LocalDate checkin;
    @NotNull
    private LocalDate checkout;

    public LocalDate getCheckin() throws DataCheckinInvalidaException {
        if (this.checkin.isAfter(this.checkout)){
            throw new DataCheckinInvalidaException();
        }
        return checkin;
    }

    public LocalDate getCheckout() throws DataCheckinInvalidaException {
        if (this.checkout.isBefore(this.checkin)){
            throw new DataCheckinInvalidaException();
        }
        return checkout;
    }
}
