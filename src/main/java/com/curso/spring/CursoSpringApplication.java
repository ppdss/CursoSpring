package com.curso.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursoSpringApplication implements CommandLineRunner{
	// ComandLineRunner permite implementar um método auxiliar para 
	// executar alguma ação quando a aplicação iniciar, no caso o Run
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursoSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
				
	}

}
