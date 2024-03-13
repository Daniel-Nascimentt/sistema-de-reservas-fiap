package br.com.fiap.quartos.msquarto.response;

import br.com.fiap.quartos.msquarto.domain.Propriedade;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class PropriedadeResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "ID da Propriedade", example = "1")
    private Long idPropriedade;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Nome da Propriedade")
    private String nomePropriedade;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Descrição das Amenidades")
    private String descricaoAmenidades;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Localizado na região em")
    private String localidade;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Endereço da propriedade")
    private String enderecoPropriedade;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Quartos da Propriedade")
    private List<QuartoResponse> quartosDaPropriedade;

    public PropriedadeResponse(Propriedade propriedade) {
        this.idPropriedade = propriedade.getId();
        this.nomePropriedade = propriedade.getNomePropriedade();
        this.descricaoAmenidades = propriedade.getDescricaoAmenidades();
        this.localidade = propriedade.getLocalidade().getNomeLocalidade();
        this.quartosDaPropriedade = propriedade.getQuartos().stream().map(QuartoResponse::new).collect(Collectors.toList());
        this.enderecoPropriedade = propriedade.getEnderecoPropriedade();
    }

    public PropriedadeResponse toResponsePropriedade(Propriedade propriedade) {
        this.idPropriedade = propriedade.getId();
        this.nomePropriedade = propriedade.getNomePropriedade();
        this.descricaoAmenidades = propriedade.getDescricaoAmenidades();
        this.localidade = propriedade.getLocalidade().getNomeLocalidade();
        this.enderecoPropriedade = propriedade.getEnderecoPropriedade();
        return this;
    }
}
