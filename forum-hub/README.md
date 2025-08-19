# ForumHub - Backend (Spring Boot)

## Rodando localmente

1. mvn clean package
2. java -jar target/forum-hub-0.0.1-SNAPSHOT.jar

H2 console (dev): http://localhost:8080/h2-console (jdbc:h2:mem:forumdb)

## Endpoints

POST /api/auth/register -> {"name":"...","email":"...","password":"..."}
POST /api/auth/login -> {"email":"...","password":"..."} -> {"token":"..."}

GET /api/topics
GET /api/topics/{id}
POST /api/topics (auth) -> {"title":"...","message":"...","courseId":1}
PUT /api/topics/{id} (auth)
DELETE /api/topics/{id} (auth)

Use Header: Authorization: Bearer <token>
