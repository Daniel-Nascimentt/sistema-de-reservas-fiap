package br.com.fiap.clientes.mscliente.domain;

import br.com.fiap.clientes.mscliente.request.ClienteRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Entity
@Table(name = "CLIENTES")
@Getter
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Endereco endereco;

    @NotBlank
    private String telefone;

    @NotBlank
    private String email;


    public Cliente(String paisOrigem, String cpf, String passaporte, boolean estrangeiro, String nomeCompleto, LocalDate dataNascimento, Endereco endereco, String telefone, String email) {
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

    public void atualizarCampos(ClienteRequest clienteRequest) {
        this.paisOrigem = clienteRequest.getPaisOrigem();
        this.cpf = clienteRequest.getCpf();
        this.passaporte = clienteRequest.getPassaporte();
        this.estrangeiro = clienteRequest.isEstrangeiro();
        this.nomeCompleto = clienteRequest.getNomeCompleto();
        this.dataNascimento = clienteRequest.getDataNascimento();
        this.endereco = clienteRequest.getEndereco().toDomain();
        this.telefone = clienteRequest.getTelefone();
        this.email = clienteRequest.getEmail();
    }
}
