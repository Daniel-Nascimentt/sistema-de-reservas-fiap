package br.com.fiap.reservas.msreservas.client;

import br.com.fiap.reservas.msreservas.response.ItemResponse;
import br.com.fiap.reservas.msreservas.response.ServicoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ms-servicos", url = "${client.url.servicos}")
public interface MsServicosClient {

    @PostMapping("/servicos/listarPorIds")
    Page<ServicoResponse> obterServicoPorListaIds(@RequestBody List<Long> ids, Pageable pageable);

    @PostMapping("/itens/listarPorIds")
    Page<ItemResponse> obterItensPorListDeIds(@RequestBody List<Long> ids, Pageable pageable);

}
