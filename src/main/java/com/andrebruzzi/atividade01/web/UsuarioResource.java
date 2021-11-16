package com.andrebruzzi.atividade01.web;

import com.andrebruzzi.atividade01.domain.Usuario;
import com.andrebruzzi.atividade01.repository.UsuarioRepository;
import com.andrebruzzi.atividade01.service.UsuarioService;
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
@RequestMapping("/usuarios")
public class UsuarioResource {
    private final Logger log = LoggerFactory.getLogger(UsuarioResource.class);

    private final UsuarioService usuarioService;

    public UsuarioResource(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * {@code GET  /usuarios/:id} : get the "id" usuario.
     *
     * @param id o id do usuario que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o usuario, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
        log.debug("REST request to get Usuario : {}", id);
        Optional<Usuario> usuario = usuarioService.findOne(id);
        if(usuario.isPresent()) {
            return ResponseEntity.ok().body(usuario.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Usuario>> getUsuarios(){
        List<Usuario> lista = usuarioService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@code PUT  /usuarios} : Atualiza um usuario existenteUpdate.
     *
     * @param usuario o usuario a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o usuario atualizado,
     * ou com status {@code 400 (Bad Request)} se o usuario não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o usuario não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario) throws URISyntaxException {
        log.debug("REST request to update Usuario : {}", usuario);
        if (usuario.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Usuario id null");
        }
        Usuario result = usuarioService.save(usuario);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new usuario.
     *
     * @param usuario the usuario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuario, or with status {@code 400 (Bad Request)} if the usuario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) throws URISyntaxException {
        log.debug("REST request to save Usuario : {}", usuario);
        if (usuario.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo usuario não pode terum ID");
        }
        Usuario result = usuarioService.save(usuario);
        return ResponseEntity.created(new URI("/api/usuarios/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Usuario> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Usuario> savedNotes = new ArrayList<>();
        List<Usuario> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Usuario::parseNote).collect(Collectors.toList());
        usuarioService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" usuario.
     *
     * @param id o id do usuarios que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        log.debug("REST request to delete Usuario : {}", id);

        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

