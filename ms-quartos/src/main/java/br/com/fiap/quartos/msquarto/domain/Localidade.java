package br.com.fiap.quartos.msquarto.domain;

import br.com.fiap.quartos.msquarto.request.LocalidadeRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LOCALIDADES")
@Getter
@NoArgsConstructor
public class Localidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nomeLocalidade;

    @OneToMany(mappedBy = "localidade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Propriedade> propriedades = new ArrayList<>();

    public Localidade(String nomeLocalidade) {
        this.nomeLocalidade = nomeLocalidade;
    }

    public void atualizar(LocalidadeRequest localidadeRequest) {
        this.nomeLocalidade = localidadeRequest.getNomeLocalidade();
    }
}
