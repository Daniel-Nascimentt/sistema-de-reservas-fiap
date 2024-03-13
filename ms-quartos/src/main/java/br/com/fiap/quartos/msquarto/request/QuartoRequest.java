package br.com.fiap.quartos.msquarto.request;

import br.com.fiap.quartos.msquarto.domain.Quarto;
import br.com.fiap.quartos.msquarto.domain.TipoQuarto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuartoRequest {

    @NotNull
    private TipoQuarto tipoQuarto;

    @NotBlank
    private String descricaoQuarto;

    @NotNull
    private BanheiroRequest banheiro;

    @NotNull
    private Long propriedadeId;

    public Quarto toDomain(){
        return new Quarto(
                this.tipoQuarto,
                this.descricaoQuarto,
                this.banheiro.toDomain()
        );
    }

}
