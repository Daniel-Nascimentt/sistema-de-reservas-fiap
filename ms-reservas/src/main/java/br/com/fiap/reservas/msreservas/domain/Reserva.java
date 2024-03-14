package br.com.fiap.reservas.msreservas.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    @OneToMany(mappedBy = "reserva")
    private List<DisponibilidadeQuarto> quartos = new ArrayList<>();
    @NotNull
    private LocalDate checkin;
    @NotNull
    private LocalDate checkout;
    @OneToMany(mappedBy = "reserva")
    private List<ServicosOpcionais> servicosOpcionais = new ArrayList<>();


}
