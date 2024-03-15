package br.com.fiap.servicos.hospedagem.msservicosopcionais.domain;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.request.ServicoRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "SERVICOS")
@Getter
@NoArgsConstructor
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nomeServico;

    @NotBlank
    private String descricaoServico;

    @NotNull
    private BigDecimal valorServico;

    @NotNull
    private Long idPropriedade;

    public Servico(String nomeServico, String descricaoServico, BigDecimal valorServico, Long idPropriedade) {
        this.nomeServico = nomeServico;
        this.descricaoServico = descricaoServico;
        this.valorServico = valorServico;
        this.idPropriedade = idPropriedade;
    }

    public void atualizar(ServicoRequest servicoRequest) {
        this.nomeServico = servicoRequest.getNomeServico();
        this.descricaoServico = servicoRequest.getDescricaoServico();
        this.valorServico = servicoRequest.getValorServico();
    }
}
