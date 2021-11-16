package com.andrebruzzi.atividade01.repository;

import com.andrebruzzi.atividade01.domain.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    
}
