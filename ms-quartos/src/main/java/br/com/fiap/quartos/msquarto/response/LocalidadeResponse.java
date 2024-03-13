package br.com.fiap.quartos.msquarto.response;

import br.com.fiap.quartos.msquarto.domain.Localidade;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema
public class LocalidadeResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long idLocalidade;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String nomeLocalidade;

    public LocalidadeResponse(Localidade localidade){
        this.idLocalidade = localidade.getId();
        this.nomeLocalidade = getNomeLocalidade();
    }

}
