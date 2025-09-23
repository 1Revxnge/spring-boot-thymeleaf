package com.example.app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.app.model.User;

// Interface de repositório para a entidade User.
// Extende MongoRepository para herdar métodos prontos de CRUD
// <User, String> indica que a entidade é User e o tipo do ID é String
public interface UserRepository extends MongoRepository<User, String> {

    // Método para buscar um usuário pelo username
    // Retorna Optional para tratar a possibilidade de não existir
    Optional<User> findByUsername(String username);

    // Método para buscar um usuário pelo email
    Optional<User> findByEmail(String email);

    // Verifica se um username já existe no banco
    boolean existsByUsername(String username);

    // Verifica se um email já existe no banco
    boolean existsByEmail(String email);
}
