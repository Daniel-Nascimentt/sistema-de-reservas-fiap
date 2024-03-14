package br.com.fiap.reservas.msreservas.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public enum TipoBanheiro {

    SIMPLES,
    LUXO,
    PREMIUM;

    String nome;

}
