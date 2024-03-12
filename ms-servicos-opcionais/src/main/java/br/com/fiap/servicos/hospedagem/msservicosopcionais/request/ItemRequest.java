package br.com.fiap.servicos.hospedagem.msservicosopcionais.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ItemRequest {

    @NotBlank
    private String nomeItem;

    @NotBlank
    private String descricaoItem;

    @NotNull
    private BigDecimal valorItem;

}
