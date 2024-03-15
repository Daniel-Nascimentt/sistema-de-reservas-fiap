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
    @ManyToOne(fetch = FetchType.LAZY)
    private Reserva reserva;
    @NotNull
    private BigDecimal valor;

    public OpcionaisReserva(ServicoResponse servico, Reserva reserva) {
        this.idOpcional = gerarIdServico(servico.getId());
        this.reserva = reserva;
        this.valor = servico.getValorServico();
    }

    public OpcionaisReserva(ItemResponse item, Reserva reserva) {
        this.idOpcional = gerarIdItem(item.getId());
        this.reserva = reserva;
        this.valor = item.getValorItem();
    }

    public String gerarIdServico(Long idServico){
        return "S_".concat(String.valueOf(idServico));
    }

    public String gerarIdItem(Long idItem){
        return "I_".concat(String.valueOf(idItem));
    }

}

