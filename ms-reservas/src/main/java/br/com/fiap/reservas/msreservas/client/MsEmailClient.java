package br.com.fiap.reservas.msreservas.client;

import br.com.fiap.reservas.msreservas.request.EmailRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-email", url = "${client.url.email}")
public interface MsEmailClient {

    @PostMapping
    void enviarEmail(@RequestBody @Valid EmailRequest request);

}
