package br.com.fiap.servicos.hospedagem.msservicosopcionais.domain;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.request.ItemRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "ITENS")
@Getter
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nomeItem;

    @NotBlank
    private String descricaoItem;

    @NotNull
    private BigDecimal valorItem;

    @NotNull
    private Long idPropriedade;

    public Item(String nomeItem, String descricaoItem, BigDecimal valorItem, Long idPropriedade) {
        this.nomeItem = nomeItem;
        this.descricaoItem = descricaoItem;
        this.valorItem = valorItem;
        this.idPropriedade = idPropriedade;
    }

    public void atualizar(ItemRequest itemRequest) {
        this.nomeItem = itemRequest.getNomeItem();
        this.descricaoItem = itemRequest.getDescricaoItem();
        this.valorItem = itemRequest.getValorItem();
        this.idPropriedade = itemRequest.getIdPropriedade();
    }
}
