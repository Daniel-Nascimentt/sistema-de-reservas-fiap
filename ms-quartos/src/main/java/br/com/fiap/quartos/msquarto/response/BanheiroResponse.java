package br.com.fiap.quartos.msquarto.response;

import br.com.fiap.quartos.msquarto.domain.Banheiro;
import br.com.fiap.quartos.msquarto.domain.TipoBanheiro;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema
public class BanheiroResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "ID do Banheiro", example = "1")
    private Long idBanheiro;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Tipo do Banheiro")
    private TipoBanheiro tipoBanheiro;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Descrição do Banheiro")
    private String descricaoBanheiro;

    public BanheiroResponse(Banheiro banheiro) {
        this.idBanheiro = banheiro.getId();
        this.tipoBanheiro = banheiro.getTipoBanheiro();
        this.descricaoBanheiro = banheiro.getDescricaoBanheiro();
    }
}
