package br.com.fiap.quartos.msquarto.repository;

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
}
