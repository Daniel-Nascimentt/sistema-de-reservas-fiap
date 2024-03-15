package br.com.fiap.reservas.msreservas.client;

import br.com.fiap.reservas.msreservas.response.QuartoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ms-quartos", url = "${client.url.quartos}")
public interface MsQuartosClient {

    @GetMapping("/localidade/{localidadeId}")
    Page<QuartoResponse> getAllQuartosByLocalidade(@PathVariable Long localidadeId);

    @PostMapping("/quartos/listarPorIds")
    Page<QuartoResponse> obterQuartosPorListIds(@RequestBody List<Long> ids, Pageable pageable);

}
