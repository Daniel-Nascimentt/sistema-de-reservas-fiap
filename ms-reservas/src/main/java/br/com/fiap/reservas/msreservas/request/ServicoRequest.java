package br.com.fiap.reservas.msreservas.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServicoRequest {

    private Long idServico;
    private Long quantidade;

}
