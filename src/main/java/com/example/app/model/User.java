package com.example.app.model;


import java.util.ArrayList;
import java.util.List;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
public class User {
@Id
private String id;
private String username;
private String email;
private String password; // bcrypt
private List<String> roles = new ArrayList<>();
private String theme; // preferencia de tema (opcional)


// getters e setters
public String getId() { return id; }
public void setId(String id) { this.id = id; }
public String getUsername() { return username; }
public void setUsername(String username) { this.username = username; }
public String getEmail() { return email; }
public void setEmail(String email) { this.email = email; }
public String getPassword() { return password; }
public void setPassword(String password) { this.password = password; }
public List<String> getRoles() { return roles; }
public void setRoles(List<String> roles) { this.roles = roles; }
public String getTheme() { return theme; }
public void setTheme(String theme) { this.theme = theme; }
}