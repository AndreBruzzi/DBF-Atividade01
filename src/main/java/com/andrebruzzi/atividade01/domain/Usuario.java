package com.andrebruzzi.atividade01.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="table_usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nome")
    private String name;

    private String bio;
    private String cpf;
    private Instant dataNascimento;
    private Boolean isActive;

    public static Usuario parseNote(String line) {
        String[] text = line.split(",");
        Usuario note = new Usuario();
        note.setId(Long.parseLong(text[0]));
        note.setName(text[1]);
        return note;
    }

    public Long getId() {
        return this.id;
    }

}
