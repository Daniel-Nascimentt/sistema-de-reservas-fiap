package br.com.fiap.servicos.hospedagem.msservicosopcionais.response;

import br.com.fiap.servicos.hospedagem.msservicosopcionais.domain.Servico;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ServicoResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "ID do serviço")
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Nome do serviço")
    private String nomeServico;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Descrição do serviço")
    private String descricaoServico;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Valor do serviço")
    private BigDecimal valorServico;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "ID do hotel associado ao serviço")
    private Long idHotel;

    public ServicoResponse(Servico servico) {
        this.id = servico.getId();
        this.nomeServico = servico.getNomeServico();
        this.descricaoServico = servico.getDescricaoServico();
        this.valorServico = servico.getValorServico();
        this.idHotel = servico.getIdHotel();
    }
}
