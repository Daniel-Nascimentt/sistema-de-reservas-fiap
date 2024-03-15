package br.com.fiap.reservas.msreservas.response;

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Valor do item")
    private BigDecimal valorItem;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "ID da propriedade associado ao item")
    private Long idPropriedade;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Quantidade do item")
    private Long quantidade;

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
}
