package br.com.fiap.clientes.mscliente.response;

import br.com.fiap.clientes.mscliente.domain.Cliente;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ClienteResponse {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String paisOrigem;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String cpf;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String passaporte;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private boolean estrangeiro;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String nomeCompleto;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LocalDate dataNascimento;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private EnderecoResponse endereco;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String telefone;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String email;


    public ClienteResponse(Cliente cliente){
        this.paisOrigem = cliente.getPaisOrigem();
        this.cpf = cliente.getCpf();
        this.passaporte = cliente.getPassaporte() != null ? cliente.getPassaporte() : this.passaporte;
        this.estrangeiro = cliente.isEstrangeiro();
        this.nomeCompleto = cliente.getNomeCompleto();
        this.dataNascimento = cliente.getDataNascimento();
        this.endereco = new EnderecoResponse(cliente.getEndereco());
        this.telefone = cliente.getTelefone();
        this.email = cliente.getEmail();
    }

}
