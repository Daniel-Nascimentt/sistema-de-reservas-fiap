package br.com.fiap.quartos.msquarto.repository;

import java.math.BigDecimal;

public interface QuartoProjection {
    Long getQuartoId();
    String getTipoQuarto();
    String getDescricaoQuarto();
    Long getBanheiroId();
    String getDescricaoBanheiro();
    String getTipoBanheiro();
    String getNomePropriedade();
    String getEnderecoPropriedade();
    String getNomeLocalidade();
    int getTotalHospedes();
    BigDecimal getValorDiaria();
}
