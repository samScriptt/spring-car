package com.gac116.corridarua.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(length = 80)
    private String cidade;

    @Column(length = 120)
    private String patrocinador;

    // Relacionamento com Piloto (opcional, se for 1:N)
    // @OneToMany(mappedBy = "equipe")
    // private List<Piloto> pilotos;


    // Construtor vazio (necessário para JPA)
    public Equipe() {}

    // Construtor com campos (opcional)
    public Equipe(String nome, String cidade, String patrocinador) {
        this.nome = nome;
        this.cidade = cidade;
        this.patrocinador = patrocinador;
    }

    // Getters and Setters (omiti para brevidade, mas devem ser incluídos)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getPatrocinador() {
        return patrocinador;
    }

    public void setPatrocinador(String patrocinador) {
        this.patrocinador = patrocinador;
    }
}