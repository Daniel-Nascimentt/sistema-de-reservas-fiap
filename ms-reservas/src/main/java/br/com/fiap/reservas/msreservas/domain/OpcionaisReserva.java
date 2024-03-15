package br.com.fiap.reservas.msreservas.domain;

import br.com.fiap.reservas.msreservas.response.ItemResponse;
import br.com.fiap.reservas.msreservas.response.ServicoResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "OPCIONAIS_RESERVA")
@Getter
@NoArgsConstructor
public class OpcionaisReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String idOpcional;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "codigo_reserva")
    private Reserva reserva;
    @NotNull
    private BigDecimal valor;
    @NotNull
    private Long quantidade;

    public OpcionaisReserva(ServicoResponse servico, Reserva reserva, Long quantidade) {
        this.idOpcional = gerarIdServico(servico.getId());
        this.reserva = reserva;
        this.valor = servico.getValorServico();
        this.quantidade = quantidade;
    }

    public OpcionaisReserva(ItemResponse item, Reserva reserva, Long quantidade) {
        this.idOpcional = gerarIdItem(item.getId());
        this.reserva = reserva;
        this.valor = item.getValorItem();
        this.quantidade = quantidade;
    }

    public String gerarIdServico(Long idServico){
        return "S_".concat(String.valueOf(idServico));
    }

    public String gerarIdItem(Long idItem){
        return "I_".concat(String.valueOf(idItem));
    }

}

