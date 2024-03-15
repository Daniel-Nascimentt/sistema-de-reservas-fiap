package br.com.fiap.servicos.hospedagem.msservicosopcionais.request;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.domain.Servico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServicoRequest {

    @NotBlank
    private String nomeServico;

    @NotBlank
    private String descricaoServico;

    @NotNull
    private BigDecimal valorServico;

    @NotNull
    private Long idPropriedade;

    public Servico toDomain() {
        return new Servico(
          this.nomeServico,
          this.descricaoServico,
          this.valorServico,
          this.idPropriedade
        );
    }
}
