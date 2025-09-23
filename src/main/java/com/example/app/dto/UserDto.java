package com.example.app.dto;

// Classe DTO (Data Transfer Object) usada para transportar dados do usuário entre a camada de Controller e Service.
// Não representa diretamente a entidade do banco, mas sim a informação que o usuário vai enviar/receber.
public class UserDto {

    // Campos que correspondem aos inputs do formulário de cadastro/login
    private String username;         // Nome do usuário, usado para login
    private String email;            // Email do usuário, único
    private String password;         // Senha digitada pelo usuário
    private String confirmPassword;  // Campo para confirmar a senha no cadastro
    private String role;             // Role atribuída ao usuário ("USER" ou "ADMIN")

    // GETTERS E SETTERS
    // São métodos que permitem acessar e alterar os valores privados dos atributos
    // São necessários para o Spring e Thymeleaf para fazer binding dos campos do formulário

    public String getUsername() {
        return username; // Retorna o username
    }

    public void setUsername(String username) {
        this.username = username; // Define o username
    }

    public String getEmail() {
        return email; // Retorna o email
    }

    public void setEmail(String email) {
        this.email = email; // Define o email
    }

    public String getPassword() {
        return password; // Retorna a senha
    }

    public void setPassword(String password) {
        this.password = password; // Define a senha
    }

    public String getConfirmPassword() {
        return confirmPassword; // Retorna a senha de confirmação
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword; // Define a senha de confirmação
    }

    public String getRole() {
        return role; // Retorna a role do usuário
    }

    public void setRole(String role) {
        this.role = role; // Define a role do usuário
    }
}
