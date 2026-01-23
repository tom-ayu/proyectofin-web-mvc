package com.udla.coremvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import com.udla.coremvc.repositorio.UsuarioRepository;
import com.udla.coremvc.modelo.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProyectofinWebMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectofinWebMvcApplication.class, args);
	}
}