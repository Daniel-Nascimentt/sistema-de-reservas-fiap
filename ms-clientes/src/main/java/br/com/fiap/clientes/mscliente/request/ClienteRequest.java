package br.com.fiap.clientes.mscliente.request;

import br.com.fiap.clientes.mscliente.domain.Cliente;
import br.com.fiap.clientes.mscliente.exception.NumeroDePassaporteNaoInformadoException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;


@NoArgsConstructor
@Getter
@AllArgsConstructor
public class ClienteRequest {

    private static final Logger logger = LoggerFactory.getLogger(ClienteRequest.class);

    @NotBlank
    private String paisOrigem;
    @NotBlank
    @CPF
    private String cpf;
    private String passaporte;
    @NotNull
    private boolean estrangeiro;
    @NotBlank
    private String nomeCompleto;
    @NotNull
    private LocalDate dataNascimento;
    @NotNull
    private EnderecoRequest endereco;
    @NotBlank
    @Pattern(regexp = "\\d+", message = "O campo deve conter apenas números")
    private String telefone;
    @NotBlank
    private String email;


    public Cliente toDomain() throws NumeroDePassaporteNaoInformadoException {
        logger.info("Convertendo ClienteRequest para Cliente...");

        if (this.estrangeiro && (this.passaporte == null || this.passaporte.isEmpty())) {
            logger.warn("Número de passaporte não informado para cliente estrangeiro");
            throw new NumeroDePassaporteNaoInformadoException();
        }

        return new Cliente(
                this.paisOrigem,
                this.cpf,
                this.passaporte,
                this.estrangeiro,
                this.nomeCompleto,
                this.dataNascimento,
                this.endereco.toDomain(),
                this.telefone,
                this.email
        );
    }
}
