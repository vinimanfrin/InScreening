package com.vinimanfrin.inscreening.repository;

import com.vinimanfrin.inscreening.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, String> {
    boolean existsByCpfOrRg(String cpf, String rg);
}
