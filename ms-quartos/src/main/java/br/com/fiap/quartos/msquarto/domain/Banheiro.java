package br.com.fiap.quartos.msquarto.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "BANHEIROS")
@Getter
@NoArgsConstructor
public class Banheiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_banheiro")
    private TipoBanheiro tipoBanheiro;

    @NotBlank
    @Column(name = "descricao_banheiro")
    private String descricaoBanheiro;

}
