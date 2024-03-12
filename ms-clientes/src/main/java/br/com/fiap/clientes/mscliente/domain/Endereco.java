package br.com.fiap.clientes.mscliente.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ENDERECOS")
@NoArgsConstructor
@Getter
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String cep;

    @NotBlank
    private String nomeRua;

    @NotNull
    private int numero;

    @NotBlank
    private String estado;

    public Endereco(String cep, String nomeRua, int numero, String estado) {
        this.cep = cep;
        this.nomeRua = nomeRua;
        this.numero = numero;
        this.estado = estado;
    }
}
