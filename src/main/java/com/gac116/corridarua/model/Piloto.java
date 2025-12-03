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
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

    // --- CAMPOS DE LOGIN ---
    @Column(unique = true)
    private String username;
    
    private String password; // Senha criptografada

    // Construtor vazio (necessário para JPA)
    public Piloto() {}

    // --- GETTERS E SETTERS ---
    
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

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    // --- MÉTODOS QUE FALTAVAM E CAUSAVAM O ERRO ---

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}