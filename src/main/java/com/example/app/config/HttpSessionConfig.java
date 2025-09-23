package com.example.app.config;

// Importamos a anotação que indica que essa classe é de configuração do Spring
import org.springframework.context.annotation.Configuration;

// Importamos a anotação que habilita sessões HTTP armazenadas no MongoDB
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;

// Marca a classe como uma classe de configuração do Spring
@Configuration
// Habilita o suporte a sessões HTTP no MongoDB
// collectionName indica o nome da coleção onde as sessões serão armazenadas
@EnableMongoHttpSession(collectionName = "sessions")
public class HttpSessionConfig {
    // Comentário dentro da classe explicando que será usado o MongoOperations padrão do Spring Boot
    // O Spring Boot usa automaticamente as configurações de conexão com MongoDB definidas em
    // spring.data.mongodb.uri no application.properties ou application.yml
}
