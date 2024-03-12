package br.com.fiap.servicos.hospedagem.msservicosopcionais.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@Schema
public class ItemResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String nomeItem;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String descricaoItem;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal valorItem;

}
