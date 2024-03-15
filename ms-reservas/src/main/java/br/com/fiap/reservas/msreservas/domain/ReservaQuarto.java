package br.com.fiap.reservas.msreservas.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "RESERVA_QUARTO")
@Getter
@NoArgsConstructor
public class ReservaQuarto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "codigo_reserva")
    private Reserva reserva;
    @NotNull
    private Long idQuarto;
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusQuarto statusQuarto;

    public ReservaQuarto(Reserva reserva, Long idQuarto, StatusQuarto statusQuarto) {
        this.reserva = reserva;
        this.idQuarto = idQuarto;
        this.statusQuarto = statusQuarto;
    }

    public void reservar(){
        this.statusQuarto = StatusQuarto.RESERVADO;
    }

}
