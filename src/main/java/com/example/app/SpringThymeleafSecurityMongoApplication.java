package com.example.app;

// Aqui estamos importando as classes principais que vamos usar no nosso projeto Spring Boot.
import org.springframework.boot.CommandLineRunner; // Permite executar código assim que a aplicação inicia
import org.springframework.boot.SpringApplication;  // Classe principal para iniciar o Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication; // Habilita configuração automática do Spring Boot
import org.springframework.context.annotation.Bean; // Permite declarar beans gerenciados pelo Spring

import com.example.app.model.User; // Importa a classe User que representa nosso modelo de usuário
import com.example.app.service.UserService; // Serviço que lida com lógica de usuário (CRUD, registro, etc.)

// Anotação que indica que essa é a classe principal do Spring Boot
@SpringBootApplication
public class SpringThymeleafSecurityMongoApplication {

    // Método main, ponto de entrada da aplicação Java
    public static void main(String[] args) {
        // SpringApplication.run inicializa toda a aplicação Spring Boot
        SpringApplication.run(SpringThymeleafSecurityMongoApplication.class, args);
    }

    // Aqui declaramos um Bean do tipo CommandLineRunner
    // O CommandLineRunner permite executar código assim que a aplicação sobe
    @Bean
    CommandLineRunner init(UserService userService) {
        // Implementamos como lambda, que recebe os argumentos de inicialização (args)
        return args -> {
            // Verifica se já existe um usuário com username "admin"
            if (!userService.existsByUsername("admin")) {
                // Se não existir, cria um novo usuário admin
                User admin = new User();
                admin.setUsername("admin"); // Define o nome de usuário
                admin.setEmail("admin@example.com"); // Define o email
                admin.setPassword("admin123"); // Define a senha (será codificada no service)
                admin.getRoles().add("ROLE_ADMIN"); // Adiciona a role de administrador

                // Salva o usuário usando o serviço específico para bootstrap
                userService.registerFromBootstrap(admin);

                // Imprime no console para sabermos que o admin foi criado
                System.out.println("Usuário admin criado: admin / admin123");
            }
        };
    }
}
