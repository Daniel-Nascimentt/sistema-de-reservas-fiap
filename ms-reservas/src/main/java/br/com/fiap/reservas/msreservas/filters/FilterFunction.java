package br.com.fiap.reservas.msreservas.filters;

import br.com.fiap.reservas.msreservas.response.QuartoResponse;

import java.util.List;

@FunctionalInterface
public interface FilterFunction {
    List<QuartoResponse> apply(List<QuartoResponse> quartos, int quantidadeHospedes);
}
