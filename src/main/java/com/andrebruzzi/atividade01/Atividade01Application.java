package com.andrebruzzi.atividade01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.andrebruzzi.atividade01.domain.Livro;
import com.andrebruzzi.atividade01.domain.Usuario;

@SpringBootApplication
public class Atividade01Application {

	public static void main(String[] args) {
		SpringApplication.run(Atividade01Application.class, args);
		System.out.println("Hello World!");
	}

}
