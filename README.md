SpringThymeleafSecurityMongo
Sistema de autenticação e cadastro de usuários com Spring Boot, Thymeleaf, Spring Security e MongoDB. Permite cadastro de usuários e admins, com dashboard diferenciado por role.

1. Pré-requisitos
Antes de rodar o projeto, você precisa ter:
Java JDK 17+
Maven 3+
Eclipse IDE / IntelliJ IDEA
 ou outro IDE Java
Conta no MongoDB Atlas
 (gratuita funciona perfeitamente)

2. Configuração do MongoDB Atlas
Crie um cluster gratuito no MongoDB Atlas.
Crie um usuário com permissão de leitura e escrita.
Pegue a URI de conexão, que deve se parecer com:
mongodb+srv://<usuario>:<senha>@cluster0.mongodb.net/<nomeDoBanco>?retryWrites=true&w=majority
Configure no application.properties do Spring Boot:
spring.data.mongodb.uri=mongodb+srv://<usuario>:<senha>@cluster0.mongodb.net/mydb?retryWrites=true&w=majority
server.port=8080
spring.thymeleaf.cache=false
Substitua <usuario>, <senha> e <nomeDoBanco> pelos seus valores.

3. Estrutura do projeto

src/main/java/com/example/app
controller → Controladores para login, registro, dashboards
dto → Objetos de transferência de dados (UserDto)
model → Entidade User
repository → UserRepository (MongoRepository)
service → Serviços (UserService, CustomUserDetailsService)
config → Configurações (SecurityConfig, HttpSessionConfig)
src/main/resources/templates → Templates Thymeleaf
login.html, register.html, dashboard.html, admin-dashboard.html, user-dashboard.html, success.html
src/main/resources/static/css → Estilos CSS

4. Configuração de segurança
Spring Security gerencia login, logout e roles.
Roles definidas: USER e ADMIN.
URLs protegidas:
URL	Role necessária
/login, /register, /css/**	Nenhuma (permitAll)
/admin/**	ADMIN
/dashboard, /user/**	USER ou ADMIN
Logout redireciona para a página inicial (/).

5. Execução do projeto
Via IDE (Eclipse / IntelliJ)
Importe o projeto como Maven Project.
Certifique-se que o application.properties está configurado com sua URI do MongoDB Atlas.
Execute SpringThymeleafSecurityMongoApplication.java como Java Application.
Abra no navegador: http://localhost:8080
Via terminal (Maven)
mvn clean install
mvn spring-boot:run
Depois acesse http://localhost:8080
.

6. Usuário Admin inicial
O sistema cria automaticamente um usuário admin se ele não existir:
Usuário: admin
Senha: admin123
Role: ADMIN

7. Funcionalidades
Cadastro de usuário com role (USER ou ADMIN).
Login com autenticação.
Dashboards separados por role:
/dashboard → redireciona automaticamente para admin-dashboard ou user-dashboard.
Logout redireciona para página inicial.
Perfil de usuário (/user/profile) e painel de admin (/admin/panel).

8. Observações importantes
Senhas são criptografadas com BCrypt.
Sessões são armazenadas no MongoDB (@EnableMongoHttpSession).
Templates Thymeleaf são carregados de src/main/resources/templates.
CSS responsivo e centralizado, inspirado em layouts modernos (como Microsoft).
