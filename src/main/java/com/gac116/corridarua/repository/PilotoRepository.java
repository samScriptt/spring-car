package com.gac116.corridarua.repository;
import com.gac116.corridarua.model.Piloto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PilotoRepository extends JpaRepository<Piloto, Long> {
    Optional<Piloto> findByUsername(String username);
}