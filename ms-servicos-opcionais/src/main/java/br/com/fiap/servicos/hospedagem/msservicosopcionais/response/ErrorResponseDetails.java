package br.com.fiap.servicos.hospedagem.msservicosopcionais.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Detalhes da resposta de erro")
public class ErrorResponseDetails {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Título do erro")
    private String titulo;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @Schema(description = "Status HTTP do erro")
    private int status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Detalhes específicos do erro")
    private List<String> detail;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @Schema(description = "Carimbo de data e hora do erro")
    private long timestamp;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Mensagem de erro")
    private String message;

    public ErrorResponseDetails(String titulo, int status, List<String> detail, long timestamp) {
        this.titulo = titulo;
        this.status = status;
        this.detail = detail;
        this.timestamp = timestamp;
    }

    public ErrorResponseDetails(String message) {
        this.message = message;
    }
}
