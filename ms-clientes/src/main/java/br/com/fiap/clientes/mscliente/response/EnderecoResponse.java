package br.com.fiap.clientes.mscliente.response;

import br.com.fiap.clientes.mscliente.domain.Endereco;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EnderecoResponse {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String cep;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String nomeRua;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int numero;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String estado;

    public EnderecoResponse(Endereco endereco) {
        this.cep = endereco.getCep();
        this.nomeRua = endereco.getNomeRua();
        this.numero = endereco.getNumero();
        this.estado = endereco.getEstado();
    }
}
