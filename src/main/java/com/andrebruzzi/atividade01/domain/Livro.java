package com.andrebruzzi.atividade01.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="table_livro")
public class Livro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nome")
    private String name;

    private String autor;
    private String sinopse;
    private String volume;
    private int numPag;
    private int pagLidas;

    public static Livro parseNote(String line) {
        String[] text = line.split(",");
        Livro note = new Livro();
        note.setId(Long.parseLong(text[0]));
        note.setName(text[1]);
        return note;
    }

    public Long getId() {
        return this.id;
    }

}
