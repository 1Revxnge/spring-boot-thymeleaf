package com.example.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.dto.UserDto;
import com.example.app.service.UserService;

// Anotação que indica que esta classe é um Controller do Spring MVC
@Controller
public class AuthController {

    // Serviço que gerencia usuários (cadastro, verificação, etc)
    private final UserService userService;

    // Construtor para injeção de dependência do UserService
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Página inicial do sistema
    @GetMapping("/")
    public String home() {
        return "home"; // Renderiza o template home.html
    }

    // Página de login
    @GetMapping("/login")
    public String login() {
        return "login"; // Renderiza login.html
    }

    // Página de registro de usuários
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userForm", new UserDto()); 
        // Cria um objeto UserDto vazio para preencher o formulário
        return "register"; // Renderiza register.html
    }

    // Endpoint que processa o envio do formulário de registro
    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute UserDto userForm, RedirectAttributes attrs) {
        try {
            // Validação de senhas: se não coincidem, volta para o formulário
            if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
                attrs.addFlashAttribute("error", "As senhas não coincidem.");
                return "redirect:/register";
            }

            // Define a role selecionada pelo usuário, padrão é "USER"
            String role = userForm.getRole() != null ? userForm.getRole() : "USER";

            // Chama o serviço para criar o usuário com username, email, senha e role
            userService.register(userForm.getUsername(), userForm.getEmail(), userForm.getPassword(), role);

            // Redireciona para uma página que indica sucesso no cadastro
            return "redirect:/register-success";

        } catch (Exception e) {
            // Caso haja erro, adiciona mensagem e retorna ao formulário
            attrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }

    // Página que indica que o usuário foi cadastrado com sucesso
    @GetMapping("/register-success")
    public String registerSuccess() {
        return "success"; // Renderiza success.html
    }

    // Dashboard que aparece após login
    // A página varia dependendo da role do usuário (ADMIN ou USER)
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String username = authentication.getName(); // Nome do usuário logado
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")); // Verifica se é admin

        // Passa variáveis para o template
        model.addAttribute("username", username);
        model.addAttribute("isAdmin", isAdmin);

        // Se for admin, redireciona para dashboard de admin, senão para de usuário
        return isAdmin ? "admin-dashboard" : "user-dashboard";
    }

    // Página de perfil do usuário comum
    @GetMapping("/user/profile")
    public String userProfile(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "user-profile"; // Renderiza user-profile.html
    }

    // Página de painel do admin
    @GetMapping("/admin/panel")
    public String adminPanel(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "admin-panel"; // Renderiza admin-panel.html
    }
}
