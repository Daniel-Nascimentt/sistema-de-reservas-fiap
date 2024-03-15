package br.com.fiap.reservas.msreservas.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ServicoResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "ID do serviço")
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Nome do serviço")
    private String nomeServico;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Descrição do serviço")
    private String descricaoServico;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Valor do serviço")
    private BigDecimal valorServico;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "ID da propriedade associado ao serviço")
    private Long idPropriedade;
}
