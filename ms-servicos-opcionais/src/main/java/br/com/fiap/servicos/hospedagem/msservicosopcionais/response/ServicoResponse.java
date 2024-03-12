package br.com.fiap.servicos.hospedagem.msservicosopcionais.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ServicoResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String nomeServico;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String descricaoServico;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal valorServico;

}
