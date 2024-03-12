package br.com.fiap.servicos.hospedagem.msservicosopcionais.request;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.domain.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {

    @NotBlank
    private String nomeItem;

    @NotBlank
    private String descricaoItem;

    @NotNull
    private BigDecimal valorItem;

    public Item toDomain() {
        return new Item(
                this.nomeItem,
                this.descricaoItem,
                this.valorItem
        );
    }
}
