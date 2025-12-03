package com.gac116.corridarua.dto;

// DTO (Data Transfer Object) para capturar os dados do formulário de registro unificado
public class RegistroDTO {
    private String username;
    private String password;
    private String nomePiloto;
    
    // Campos de Equipe
    private Long equipeId; // Se escolher existente
    private String novaEquipeNome; // Se criar nova
    private String novaEquipeCidade;

    // --- GETTERS E SETTERS (Obrigatórios no Java) ---
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

    public String getNomePiloto() {
        return nomePiloto;
    }

    public void setNomePiloto(String nomePiloto) {
        this.nomePiloto = nomePiloto;
    }

    public Long getEquipeId() {
        return equipeId;
    }

    public void setEquipeId(Long equipeId) {
        this.equipeId = equipeId;
    }

    public String getNovaEquipeNome() {
        return novaEquipeNome;
    }

    public void setNovaEquipeNome(String novaEquipeNome) {
        this.novaEquipeNome = novaEquipeNome;
    }

    public String getNovaEquipeCidade() {
        return novaEquipeCidade;
    }

    public void setNovaEquipeCidade(String novaEquipeCidade) {
        this.novaEquipeCidade = novaEquipeCidade;
    }
}