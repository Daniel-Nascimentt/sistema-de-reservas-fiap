package br.com.fiap.reservas.msreservas.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "SERVICOS_OPCIONAIS")
@Getter
@NoArgsConstructor
public class ServicosOpcionais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long idServico;
    @ManyToOne(fetch = FetchType.LAZY)
    private Reserva reserva;
    @NotNull
    private BigDecimal valor;

}

