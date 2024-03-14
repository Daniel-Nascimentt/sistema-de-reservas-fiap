package br.com.fiap.reservas.msreservas.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public enum TipoQuarto {

    STANDARD_SIMPLES,
    STANDARD_DUPLO,
    LUXO_SIMPLES,
    LUXO_DUPLO,
    PREMIUM_SIMPLES,
    PREMIUM_DUPLO;

    String nome;

}
