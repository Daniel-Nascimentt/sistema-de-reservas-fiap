package br.com.fiap.quartos.msquarto.request;

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

    @NotBlank
    private String enderecoPropriedade;

    private Long idLocalidade;

}
