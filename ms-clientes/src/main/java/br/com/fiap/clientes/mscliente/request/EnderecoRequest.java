package br.com.fiap.clientes.mscliente.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequest {

    @NotBlank
    private String cep;
    @NotBlank
    private String nomeRua;
    @NotNull
    private int numero;
    @NotBlank
    private String estado;

}
