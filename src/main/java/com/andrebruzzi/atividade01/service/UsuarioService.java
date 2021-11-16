package com.andrebruzzi.atividade01.service;

import com.andrebruzzi.atividade01.repository.UsuarioRepository;
import com.andrebruzzi.atividade01.domain.Usuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    
    private final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAllList() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findOne(Long id) {
        log.debug("Request to get usuario: {}", id);
        return usuarioRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete usuario: {}", id);
        usuarioRepository.deleteById(id);
    }

    public Usuario save(Usuario usuario){
        log.debug("Request to save usuario: {}", usuario);
        usuario = usuarioRepository.save(usuario);
        return usuario;
    }

    public List<Usuario> saveAll(List<Usuario> usuarios) {
        log.debug("Request to save Usuario : {}", usuarios);
        usuarios = usuarioRepository.saveAll(usuarios);
        return usuarios;
    }

}
