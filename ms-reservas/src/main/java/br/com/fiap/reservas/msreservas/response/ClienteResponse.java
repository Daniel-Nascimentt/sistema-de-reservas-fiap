package br.com.fiap.reservas.msreservas.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(description = "Representação do Cliente na resposta")
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Id do cliente")
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Número de CPF do cliente")
    private String cpf;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Nome completo do cliente")
    private String nomeCompleto;

}
