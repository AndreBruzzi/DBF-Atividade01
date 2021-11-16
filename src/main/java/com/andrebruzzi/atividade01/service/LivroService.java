package com.andrebruzzi.atividade01.service;

import com.andrebruzzi.atividade01.repository.LivroRepository;
import com.andrebruzzi.atividade01.domain.Livro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    
    private final Logger log = LoggerFactory.getLogger(LivroService.class);

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<Livro> findAllList() {
        return livroRepository.findAll();
    }

    public Optional<Livro> findOne(Long id) {
        log.debug("Request to get livro: {}", id);
        return livroRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete livro: {}", id);
        livroRepository.deleteById(id);
    }

    public Livro save(Livro livro){
        log.debug("Request to save livro: {}", livro);
        livro = livroRepository.save(livro);
        return livro;
    }

    public List<Livro> saveAll(List<Livro> livros) {
        log.debug("Request to save Livro : {}", livros);
        livros = livroRepository.saveAll(livros);
        return livros;
    }

}
