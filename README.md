# ForumHub — Backend (Spring Boot)

**descrição**  
API REST para gestão de tópicos de fórum ligados a cursos. Implementado com Spring Boot, JPA e autenticação JWT.

**Tecnologias**
- Java 17, Spring Boot (Web, Data JPA, Security, Validation)
- JWT (`io.jsonwebtoken`)
- H2 (dev), Maven, Lombok

**Principais funcionalidades**
- CRUD de tópicos (cada tópico pertence a um curso e a um autor)
- Registro/login com JWT
- Endpoints públicos para leitura; mutações exigem autenticação
