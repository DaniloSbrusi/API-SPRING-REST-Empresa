package br.edu.unoesc.empresa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.edu.unoesc.empresa.service.FuncionarioService;

@SpringBootApplication
public class EmpresaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpresaApplication.class, args);
	}
	
	@Bean
	CommandLineRunner commandLineRunner(FuncionarioService servico) {
		return args -> {
			servico.popularTabelaInicial();
		};
	}
}
