package br.com.fiap.quartos.msquarto.request;

import br.com.fiap.quartos.msquarto.domain.Localidade;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocalidadeRequest {

    @NotBlank
    private String nomeLocalidade;

    public Localidade toDomain(){
        return new Localidade(this.nomeLocalidade);
    }

}
