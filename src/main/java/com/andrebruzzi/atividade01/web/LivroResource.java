package com.andrebruzzi.atividade01.web;

import com.andrebruzzi.atividade01.domain.Livro;
import com.andrebruzzi.atividade01.repository.LivroRepository;
import com.andrebruzzi.atividade01.service.LivroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/livros")
public class LivroResource {
    private final Logger log = LoggerFactory.getLogger(LivroResource.class);

    private final LivroService livroService;

    public LivroResource(LivroService livroService) {
        this.livroService = livroService;
    }

    /**
     * {@code GET  /livros/:id} : get the "id" livro.
     *
     * @param id o id do livro que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o livro, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Livro> getLivro(@PathVariable Long id) {
        log.debug("REST request to get Livro : {}", id);
        Optional<Livro> livro = livroService.findOne(id);
        if(livro.isPresent()) {
            return ResponseEntity.ok().body(livro.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Livro>> getLivros(){
        List<Livro> lista = livroService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@code PUT  /livros} : Atualiza um livro existenteUpdate.
     *
     * @param livro o livro a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o livro atualizado,
     * ou com status {@code 400 (Bad Request)} se o livro não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o livro não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Livro> updateLivro(@RequestBody Livro livro) throws URISyntaxException {
        log.debug("REST request to update Livro : {}", livro);
        if (livro.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Livro id null");
        }
        Livro result = livroService.save(livro);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new livro.
     *
     * @param livro the livro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new livro, or with status {@code 400 (Bad Request)} if the livro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Livro> createLivro(@RequestBody Livro livro) throws URISyntaxException {
        log.debug("REST request to save Livro : {}", livro);
        if (livro.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo livro não pode terum ID");
        }
        Livro result = livroService.save(livro);
        return ResponseEntity.created(new URI("/api/livros/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Livro> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Livro> savedNotes = new ArrayList<>();
        List<Livro> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Livro::parseNote).collect(Collectors.toList());
        livroService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" livro.
     *
     * @param id o id do livros que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        log.debug("REST request to delete Livro : {}", id);

        livroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

