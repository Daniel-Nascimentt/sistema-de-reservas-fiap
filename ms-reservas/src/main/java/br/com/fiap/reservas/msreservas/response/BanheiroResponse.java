package br.com.fiap.reservas.msreservas.response;

import br.com.fiap.reservas.msreservas.domain.TipoBanheiro;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema
@AllArgsConstructor
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
}
