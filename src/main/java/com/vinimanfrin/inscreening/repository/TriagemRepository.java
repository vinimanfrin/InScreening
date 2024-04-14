package com.vinimanfrin.inscreening.repository;

import com.vinimanfrin.inscreening.models.Triagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriagemRepository extends JpaRepository<Triagem, String> {
}
