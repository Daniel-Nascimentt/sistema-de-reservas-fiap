package br.com.fiap.servicos.hospedagem.msservicosopcionais.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Getter
@NoArgsConstructor
public class ServicoRequest {

    @NotBlank
    private String nomeServico;

    @NotBlank
    private String descricaoServico;

    @NotNull
    private BigDecimal valorServico;

}
