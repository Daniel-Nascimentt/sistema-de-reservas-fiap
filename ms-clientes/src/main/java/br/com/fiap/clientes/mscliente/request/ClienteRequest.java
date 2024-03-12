package br.com.fiap.clientes.mscliente.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@NotBlank
@NoArgsConstructor
@Getter
public class ClienteRequest {

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

    public ClienteRequest(String paisOrigem, String cpf, boolean estrangeiro, String nomeCompleto, LocalDate dataNascimento, EnderecoRequest endereco, String telefone, String email) {
        this.paisOrigem = paisOrigem;
        this.cpf = cpf;
        this.estrangeiro = estrangeiro;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    public ClienteRequest(String paisOrigem, String cpf, String passaporte, boolean estrangeiro, String nomeCompleto, LocalDate dataNascimento, EnderecoRequest endereco, String telefone, String email) {
        this.paisOrigem = paisOrigem;
        this.cpf = cpf;
        this.passaporte = passaporte;
        this.estrangeiro = estrangeiro;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }
}
