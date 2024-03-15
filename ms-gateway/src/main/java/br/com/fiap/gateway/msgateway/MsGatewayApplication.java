package br.com.fiap.gateway.msgateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsGatewayApplication {

	@Value("${host.ms.client}")
	private String hostMsCliente;
	@Value("${host.ms.quartos}")
	private String hostMsQuartos;
	@Value("${host.ms.reservas}")
	private String hostMsReservas;
	@Value("${host.ms.servicos}")
	private String hostMsServicos;

	public static void main(String[] args) {
		SpringApplication.run(MsGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder
				.routes()
				.route(r -> r.path("/ms-cliente/**").uri(this.hostMsCliente))
				.route(r -> r.path("/ms-quartos/**").uri(this.hostMsQuartos))
				.route(r -> r.path("/ms-reservas/**").uri(this.hostMsReservas))
				.route(r -> r.path("/ms-servicos/**").uri(this.hostMsServicos))
				.build();
	}

}
