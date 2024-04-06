package com.genildo.minhasfinancas.model.entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table (name = "lancamento", schema = "financas")
public class Lancamento {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column ( name = "id")
    private Long id;

    @Column ( name = "descricao")
    private String descricao;

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

    @Column ( name = "tipo")
    @Enumerated (value = EnumType.STRING )
    private TipoLancamento tipo;

    @Override
    public String toString() {
        return "Lancamento{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", mes=" + mes +
                ", ano=" + ano +
                ", usuario=" + usuario +
                ", valor=" + valor +
                ", dataCadastro=" + dataCadastro +
                ", tipo=" + tipo +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lancamento that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getDescricao(), that.getDescricao()) && Objects.equals(getMes(), that.getMes()) && Objects.equals(getAno(), that.getAno()) && Objects.equals(getUsuario(), that.getUsuario()) && Objects.equals(getValor(), that.getValor()) && Objects.equals(getDataCadastro(), that.getDataCadastro()) && getTipo() == that.getTipo() && getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescricao(), getMes(), getAno(), getUsuario(), getValor(), getDataCadastro(), getTipo(), getStatus());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public StatusLancamento getStatus() {
        return status;
    }

    public void setStatus(StatusLancamento status) {
        this.status = status;
    }

    @Column ( name = "status")
    @Enumerated (value = EnumType.STRING)
    private StatusLancamento status;
}
