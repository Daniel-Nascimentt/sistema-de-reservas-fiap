package br.com.fiap.reservas.msreservas.client;

import br.com.fiap.reservas.msreservas.response.QuartoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-quartos", url = "${client.url.quartos}")
public interface MsQuartosClient {

    @GetMapping("/localidade/{localidadeId}")
    Page<QuartoResponse> getAllQuartosByLocalidade(@PathVariable Long localidadeId);

}
