package com.genildo.minhasfinancas.model.entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table (name = "lancamento", schema = "financas")
public class Lancamento {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column ( name = "id")
    private Long id;

    @Column ( name = "mes")
    private Integer mes;

    @Column ( name = "ano")
    private Integer ano;

    @ManyToOne
    @JoinColumn ( name = "id_usuario")
    private Usuario usuario;

    @Column ( name = "valor")
    private BigDecimal valor;

    @Column (name = "data_cadastro")
    @Convert ( converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataCadastro;

}
