package br.com.fiap.clientes.mscliente.response;

import br.com.fiap.clientes.mscliente.domain.Endereco;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(description = "Representação do Endereço na resposta")
public class EnderecoResponse {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "CEP do endereço")
    private String cep;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Nome da rua do endereço")
    private String nomeRua;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Número do endereço")
    private Integer numero;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Estado do endereço")
    private String estado;

    public EnderecoResponse(Endereco endereco) {
        this.cep = endereco.getCep();
        this.nomeRua = endereco.getNomeRua();
        this.numero = endereco.getNumero();
        this.estado = endereco.getEstado();
    }
}
