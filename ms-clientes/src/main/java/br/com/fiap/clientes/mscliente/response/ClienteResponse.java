package br.com.fiap.clientes.mscliente.response;

import br.com.fiap.clientes.mscliente.domain.Cliente;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Schema(description = "Representação do Cliente na resposta")
public class ClienteResponse {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Id do cliente")
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "País de origem do cliente")
    private String paisOrigem;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Número de CPF do cliente")
    private String cpf;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Número de passaporte do cliente")
    private String passaporte;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Indica se o cliente é estrangeiro")
    private boolean estrangeiro;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Nome completo do cliente")
    private String nomeCompleto;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Data de nascimento do cliente")
    private LocalDate dataNascimento;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Endereço do cliente")
    private EnderecoResponse endereco;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Número de telefone do cliente")
    private String telefone;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Endereço de e-mail do cliente")
    private String email;


    public ClienteResponse(Cliente cliente){
        this.id = cliente.getId();
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
