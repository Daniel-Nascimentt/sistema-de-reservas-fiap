package br.com.fiap.servicos.hospedagem.msservicosopcionais.response;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.domain.Item;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Getter
@Schema
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "ID do item")
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Nome do item")
    private String nomeItem;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Descrição do item")
    private String descricaoItem;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Valor do item")
    private BigDecimal valorItem;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "ID do hotel associado ao item")
    private Long idHotel;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.nomeItem = item.getNomeItem();
        this.descricaoItem = item.getDescricaoItem();
        this.valorItem = item.getValorItem();
        this.idHotel = item.getIdHotel();
    }
}
