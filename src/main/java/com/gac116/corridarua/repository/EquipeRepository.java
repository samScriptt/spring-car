package com.gac116.corridarua.repository;

import com.gac116.corridarua.model.Equipe; // Agora deve resolver após criar Equipe.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações CRUD da entidade Equipe.
 */
@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {
    // Você pode adicionar métodos de busca personalizados aqui, se necessário.
    // Exemplo: Equipe findByNome(String nome);
}