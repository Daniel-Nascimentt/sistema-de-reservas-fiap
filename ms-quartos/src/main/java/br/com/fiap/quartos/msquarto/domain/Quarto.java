package br.com.fiap.quartos.msquarto.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "QUARTOS")
@Getter
@NoArgsConstructor
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_quarto")
    private TipoQuarto tipoQuarto;

    @NotBlank
    @Column(name = "descricao_quarto")
    private String descricaoQuarto;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Banheiro banheiro;

    @ManyToMany
    @JoinTable(
            name = "quarto_propriedade",
            joinColumns = @JoinColumn(name = "quarto_id"),
            inverseJoinColumns = @JoinColumn(name = "propriedade_id")
    )
    @Column(name = "propriedades_com_quarto")
    private List<Propriedade> propriedadesComQuarto;


}
