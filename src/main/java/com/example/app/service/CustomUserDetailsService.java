package com.example.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.app.model.User;
import com.example.app.repository.UserRepository;

// Anotação que indica que esta classe é um serviço do Spring
// Ele implementa UserDetailsService para integração com Spring Security
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Repositório de usuários para buscar informações no banco
    private final UserRepository repo;

    // Construtor com injeção de dependência
    public CustomUserDetailsService(UserRepository repo) { 
        this.repo = repo; 
    }

    // Método obrigatório da interface UserDetailsService
    // É chamado pelo Spring Security para carregar um usuário baseado no username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Busca o usuário no banco pelo username, lança exceção se não encontrado
        User u = repo.findByUsername(username)
                     .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // Converte a lista de roles (ex: ROLE_USER, ROLE_ADMIN) em GrantedAuthority
        // Spring Security usa GrantedAuthority para controle de acesso
        List<GrantedAuthority> authorities = u.getRoles().stream()
                                             .map(SimpleGrantedAuthority::new)
                                             .collect(Collectors.toList());

        // Retorna um objeto UserDetails do Spring Security contendo username, senha e roles
        // Esse objeto é usado internamente pelo Spring para autenticação e autorização
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), // login do usuário
                u.getPassword(), // senha criptografada
                authorities      // lista de permissões
        );
    }
}
