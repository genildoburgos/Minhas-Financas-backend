package com.genildo.minhasfinancas.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table ( name = "usuario", schema = "financas")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Usuario {
    @Id
    @Column (name = "id")
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column ( name = "nome")
    private String nome;

    @Column( name = "email")
    private String email;

    @Column( name = "senha")
    private String senha;

}
