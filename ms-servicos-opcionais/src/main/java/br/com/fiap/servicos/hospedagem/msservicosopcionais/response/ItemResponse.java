package br.com.fiap.servicos.hospedagem.msservicosopcionais.response;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.domain.Item;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Getter
@Schema
@NoArgsConstructor
public class ItemResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String nomeItem;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String descricaoItem;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal valorItem;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.nomeItem = item.getNomeItem();
        this.descricaoItem = item.getDescricaoItem();
        this.valorItem = item.getValorItem();
    }
}
