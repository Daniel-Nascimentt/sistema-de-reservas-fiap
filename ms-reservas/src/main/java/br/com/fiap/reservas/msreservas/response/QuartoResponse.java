package br.com.fiap.reservas.msreservas.response;

import br.com.fiap.reservas.msreservas.domain.TipoQuarto;
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
public class QuartoResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "ID do Quarto", example = "1")
    private Long idQuarto;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Tipo do Quarto")
    private TipoQuarto tipoQuarto;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Descrição do Quarto")
    private String descricaoQuarto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Informações do Banheiro")
    private BanheiroResponse banheiroResponse;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Nome da propriedade onde esta localizado o quarto")
    private String nomePropriedade;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Endereço da propriedade onde esta localizado o quarto")
    private String enderecoPropriedade;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Regiao onde esta localizado o quarto")
    private String localidadeQuarto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Total de hospedes que o quarto comporta")
    private int totalHospedes;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Valor da diaria do quarto")
    private BigDecimal valorDiaria;

    public QuartoResponse(Long idQuarto, int totalHospedes, String enderecoPropriedade) {
        this.idQuarto = idQuarto;
        this.totalHospedes = totalHospedes;
        this.enderecoPropriedade = enderecoPropriedade;
    }
}
