package br.com.fiap.quartos.msquarto.response;

import br.com.fiap.quartos.msquarto.domain.Quarto;
import br.com.fiap.quartos.msquarto.domain.TipoQuarto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

    public QuartoResponse(Quarto quarto) {
        this.idQuarto = quarto.getId();
        this.tipoQuarto = quarto.getTipoQuarto();
        this.descricaoQuarto = quarto.getDescricaoQuarto();
        this.banheiroResponse = new BanheiroResponse(quarto.getBanheiro());
    }
}
