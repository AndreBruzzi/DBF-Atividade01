package com.andrebruzzi.atividade01.repository;

import com.andrebruzzi.atividade01.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
