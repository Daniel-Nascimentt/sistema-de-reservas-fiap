package br.com.fiap.quartos.msquarto.request;

import br.com.fiap.quartos.msquarto.domain.Quarto;
import br.com.fiap.quartos.msquarto.domain.TipoQuarto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    @NotNull
    @Positive
    private int totalHospedes;

    @NotNull
    @Positive
    private BigDecimal valorDiaria;

    public Quarto toDomain(){
        return new Quarto(
                this.tipoQuarto,
                this.descricaoQuarto,
                this.banheiro.toDomain(),
                this.totalHospedes,
                this.valorDiaria
        );
    }

}
