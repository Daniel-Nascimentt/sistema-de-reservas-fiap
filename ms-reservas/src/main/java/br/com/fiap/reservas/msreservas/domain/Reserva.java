package br.com.fiap.reservas.msreservas.domain;

import br.com.fiap.reservas.msreservas.exception.ClienteInvalidoException;
import br.com.fiap.reservas.msreservas.exception.DataCheckinInvalidaException;
import br.com.fiap.reservas.msreservas.exception.OperacaoReservaNaoPermitidaException;
import br.com.fiap.reservas.msreservas.request.NovaReservaRequest;
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
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservaQuarto> quartos = new ArrayList<>();
    @NotNull
    private LocalDate checkin;
    @NotNull
    private LocalDate checkout;
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpcionaisReserva> servicosOpcionais = new ArrayList<>();
    @NotNull
    private LocalDateTime dataPreReserva;
    @NotNull
    private Long idcliente;
    @NotNull
    private BigDecimal valorTotalReserva = new BigDecimal("0");

    public Reserva(LocalDate checkin, LocalDate checkout, LocalDateTime dataPreReserva, Long idCliente) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.dataPreReserva = dataPreReserva;
        this.idcliente = idCliente;
    }

    public void setCodigoReserva(UUID codigoReserva) {
        this.codigoReserva = codigoReserva;
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

    public void validarStatusReservado() throws OperacaoReservaNaoPermitidaException {
        boolean quartosReservados = this.quartos.stream()
                .anyMatch(q -> q.getStatusQuarto().equals(StatusQuarto.RESERVADO));
        if (quartosReservados) {
            throw new OperacaoReservaNaoPermitidaException();
        }
    }

    public void clearOpcional() {
        this.servicosOpcionais.clear();
    }

    public void clearQuartos() {
        this.quartos.clear();
    }

    public void zerarValorDiaria() {
        this.valorTotalReserva = new BigDecimal("0");
    }

    public void atualizarPreReserva(NovaReservaRequest request) throws DataCheckinInvalidaException {
        this.dataPreReserva = LocalDateTime.now();
        this.checkin = request.getCheckin();
        this.checkout = request.getCheckout();
        this.idcliente = request.getIdCliente();
    }

    public void validarTitularidade(Long idCliente) throws ClienteInvalidoException {
        if(!this.idcliente.equals(idCliente)){
            throw new ClienteInvalidoException();
        }
    }
}
