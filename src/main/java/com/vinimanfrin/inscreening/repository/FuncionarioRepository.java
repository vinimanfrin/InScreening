package com.vinimanfrin.inscreening.repository;

import com.vinimanfrin.inscreening.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, String> {
}
