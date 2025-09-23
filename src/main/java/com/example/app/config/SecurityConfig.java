package com.example.app.config;

// Importações necessárias para a configuração de segurança do Spring
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Indica que esta classe é uma classe de configuração do Spring
@Configuration
public class SecurityConfig {

    // Configura o filtro de segurança (SecurityFilterChain) que o Spring Security vai usar
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Configura quais URLs podem ser acessadas por quem
            .authorizeHttpRequests(auth -> auth
                // Permite acesso sem autenticação a páginas de registro, login e arquivos CSS
                .requestMatchers("/register", "/login", "/css/**").permitAll()
                // Somente usuários com ROLE_ADMIN podem acessar URLs que começam com /admin/
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Todas as outras URLs exigem usuário autenticado (USER ou ADMIN)
                .anyRequest().hasAnyRole("USER", "ADMIN")
            )
            // Configuração do formulário de login
            .formLogin(form -> form
                .loginPage("/login") // Página customizada de login
                .defaultSuccessUrl("/dashboard", true) // Redireciona para /dashboard após login
                .permitAll() // Permite que todos vejam a página de login
            )
            // Configuração do logout
            .logout(logout -> logout
                .logoutUrl("/") // URL para fazer logout (poderia ser /logout também)
                .logoutSuccessUrl("/") // Redireciona para a home após logout
                .permitAll() // Todos podem acessar o logout
            );

        // Constrói e retorna a configuração de segurança
        return http.build();
    }

    // Bean que será usado para codificar senhas com BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean para criar um AuthenticationManager que será usado pelo Spring Security
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
