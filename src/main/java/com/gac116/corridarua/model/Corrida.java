package com.gac116.corridarua.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Corrida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false)
    private LocalDate data; // Use LocalDate para datas sem hora

    @Enumerated(EnumType.STRING) // Salva o texto ("PLANEJADA") no banco em vez de números (0, 1...)
    @Column(length = 20)
    private StatusCorrida status = StatusCorrida.PLANEJADA; // Valor padrão

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private CategoriaCorrida categoria = CategoriaCorrida.STREET; // Valor padrão

    // Relacionamento com Pista
    @ManyToOne
    @JoinColumn(name = "pista_id", nullable = false)
    private Pista pista;

    // --- Enums Internos ou Externos (Definidos aqui para facilidade) ---
    
    public enum StatusCorrida {
        PLANEJADA,
        EM_ANDAMENTO,
        ENCERRADA
    }

    public enum CategoriaCorrida {
        STREET,
        DRAG,
        DRIFT
    }

    // --- Getters e Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public StatusCorrida getStatus() { return status; }
    public void setStatus(StatusCorrida status) { this.status = status; }

    public CategoriaCorrida getCategoria() { return categoria; }
    public void setCategoria(CategoriaCorrida categoria) { this.categoria = categoria; }

    public Pista getPista() { return pista; }
    public void setPista(Pista pista) { this.pista = pista; }
}