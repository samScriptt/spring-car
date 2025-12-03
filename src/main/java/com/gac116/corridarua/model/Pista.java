package com.gac116.corridarua.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Pista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 80)
    private String cidade;

    // DecimalField(max_digits=5, decimal_places=2) -> BigDecimal
    @Column(name = "extensao_km", precision = 5, scale = 2)
    private BigDecimal extensaoKm;

    // Relacionamento inverso (opcional, mas útil para listar corridas de uma pista)
    @OneToMany(mappedBy = "pista")
    private List<Corrida> corridas;

    // --- Getters e Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public BigDecimal getExtensaoKm() { return extensaoKm; }
    public void setExtensaoKm(BigDecimal extensaoKm) { this.extensaoKm = extensaoKm; }

    public List<Corrida> getCorridas() { return corridas; }
    public void setCorridas(List<Corrida> corridas) { this.corridas = corridas; }
}