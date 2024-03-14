package br.com.fiap.reservas.msreservas.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "DISPONIBILIDADE_QUARTOS")
@Getter
@NoArgsConstructor
public class DisponibilidadeQuarto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Reserva reserva;
    @NotNull
    private Long idQuarto;
    @NotNull
    private StatusQuarto statusQuarto;

}
