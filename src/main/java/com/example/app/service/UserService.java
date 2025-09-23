package com.example.app.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.app.model.User;
import com.example.app.repository.UserRepository;

// Anotação @Service indica que esta classe é um serviço gerenciado pelo Spring
// Ela contém a lógica de negócio relacionada aos usuários
@Service
public class UserService {

    // Repositório para acessar os dados de usuários no banco
    private final UserRepository repo;

    // PasswordEncoder para criptografar senhas antes de salvar
    private final PasswordEncoder passwordEncoder;

    // Construtor com injeção de dependência do repositório e encoder
    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    // Registro padrão como USER
    // Mantém compatibilidade com chamadas antigas que não informam a role
    public User register(String username, String email, String rawPassword) {
        return register(username, email, rawPassword, "USER");
    }

    // Registro com role dinâmica
    // Permite criar usuário comum ou admin, baseado na string role
    public User register(String username, String email, String rawPassword, String role) {
        // Valida se o username já existe
        if (repo.existsByUsername(username)) throw new IllegalArgumentException("Username já existe");
        // Valida se o email já existe
        if (repo.existsByEmail(email)) throw new IllegalArgumentException("Email já existe");
        // Valida se a senha atende ao mínimo de caracteres
        if (rawPassword == null || rawPassword.length() < 6) throw new IllegalArgumentException("Senha muito curta (mínimo 6 caracteres)");

        // Cria objeto User e configura atributos
        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        // Criptografa a senha antes de salvar
        u.setPassword(passwordEncoder.encode(rawPassword));
        // Adiciona a role adequada (ROLE_USER ou ROLE_ADMIN)
        u.getRoles().add("ROLE_" + role.toUpperCase());

        // Salva o usuário no banco de dados e retorna
        return repo.save(u);
    }

    // Usado no CommandLineRunner para criar um admin inicial
    public User registerFromBootstrap(User rawUser) {
        // Se já existir, apenas retorna
        if (repo.existsByUsername(rawUser.getUsername())) return rawUser;
        // Criptografa a senha antes de salvar
        rawUser.setPassword(passwordEncoder.encode(rawUser.getPassword()));
        return repo.save(rawUser);
    }

    // Consulta usuário por username
    public Optional<User> findByUsername(String username) { 
        return repo.findByUsername(username); 
    }

    // Verifica se o username já existe
    public boolean existsByUsername(String username) { 
        return repo.existsByUsername(username); 
    }
}
