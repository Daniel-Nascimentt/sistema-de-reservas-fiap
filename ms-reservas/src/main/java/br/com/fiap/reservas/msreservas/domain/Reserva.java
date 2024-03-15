package br.com.fiap.reservas.msreservas.domain;

import br.com.fiap.reservas.msreservas.request.NovaReservaRequest;
import br.com.fiap.reservas.msreservas.response.ItemResponse;
import br.com.fiap.reservas.msreservas.response.ServicoResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "RESERVAS")
@Getter
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID codigoReserva;
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<ReservaQuarto> quartos = new ArrayList<>();
    @NotNull
    private LocalDate checkin;
    @NotNull
    private LocalDate checkout;
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<OpcionaisReserva> servicosOpcionais = new ArrayList<>();
    @NotNull
    private LocalDateTime dataPreReserva;
    @NotNull
    private BigDecimal valorTotalReserva = new BigDecimal("0");

    public Reserva(LocalDate checkin, LocalDate checkout, LocalDateTime dataPreReserva) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.dataPreReserva = dataPreReserva;
    }

    public void addOpcional(OpcionaisReserva opcionaisReserva) {
        this.servicosOpcionais.add(opcionaisReserva);
    }

    public void addQuartoAReserva(ReservaQuarto reservaQuarto){
        this.quartos.add(reservaQuarto);
    }

    public void somarAoTotalReserva(BigDecimal valor){
        this.valorTotalReserva = this.valorTotalReserva.add(valor);
    }

    public void confirmarReserva() {
        this.quartos.forEach(ReservaQuarto::reservar);
    }
}
