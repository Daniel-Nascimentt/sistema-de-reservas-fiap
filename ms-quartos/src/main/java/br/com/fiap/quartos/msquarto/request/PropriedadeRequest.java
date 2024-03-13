package br.com.fiap.quartos.msquarto.request;

import br.com.fiap.quartos.msquarto.domain.Propriedade;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PropriedadeRequest {

    @NotBlank
    private String nomePropriedade;

    @NotBlank
    private String descricaoAmenidades;

    private Long idLocalidade;

}
