package br.com.fiap.quartos.msquarto.request;

import br.com.fiap.quartos.msquarto.domain.Banheiro;
import br.com.fiap.quartos.msquarto.domain.TipoBanheiro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BanheiroRequest {

    @NotNull
    private TipoBanheiro tipoBanheiro;

    @NotBlank
    private String descricaoBanheiro;

    public Banheiro toDomain() {
        return new Banheiro(
            this.tipoBanheiro,
            this.descricaoBanheiro
        );
    }
}
