package br.com.fiap.quartos.msquarto.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PROPRIEDADES")
@Getter
@NoArgsConstructor
public class Propriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nome_propriedade")
    private String nomePropriedade;

    @NotBlank
    @Column(name = "descricao_amenidades")
    private String descricaoAmenidades;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Localidade localidade;

    @ManyToMany
    private List<Quarto> quartos = new ArrayList<>();

}
