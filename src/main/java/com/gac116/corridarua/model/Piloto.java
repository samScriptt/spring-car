package com.gac116.corridarua.model;

import jakarta.persistence.*;

@Entity
public class Piloto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(length = 30)
    private String documento;

    @Column(length = 10)
    private String categoria;

    // Relacionamento N:1 com Equipe
    @ManyToOne
    @JoinColumn(name = "equipe_id") // Chave estrangeira
    private Equipe equipe; // <-- Símbolo Equipe corrigido pela importação/criação da classe Equipe

    // Construtor vazio (necessário para JPA)
    public Piloto() {}

    @Column(unique = true)
private String username;
private String password; // Senha criptografada

    // Construtor, Getters and Setters (omiti para brevidade, mas devem ser incluídos)
    // ...
    
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Equipe getEquipe() { // <-- Símbolo Equipe corrigido
        return equipe;
    }

    public void setEquipe(Equipe equipe) { // <-- Símbolo Equipe corrigido
        this.equipe = equipe;
    }
}