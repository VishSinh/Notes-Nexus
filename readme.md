# NOTES NEXUS - BACKEND

### [Link to Frontend](https://github.com/VishSinh/NotesNexus-FE)

## TECHNOLOGIES
- Spring Boot
- Spring Data JPA
- Spring Web
- MySQL
- Docker

## SETUP
1. Clone the repository
2. Open the project in your IDE
3. Start the Spring Boot application and MySQL server using Docker Compose
4. The application will be running on `http://127.0.0.1:8000`

```bash
docker compose up --build
```


## DATABASE

![DB Schema](https://github.com/VishSinh/Notes-BE/blob/main/DB_Schema.png)

## API REFERENCE
### [Link to Postman](https://www.postman.com/joint-operations-engineer-19861059/workspace/sticky-notes/collection/29105784-44930caf-f73c-4da6-a07f-3969d894c677?action=share&creator=29105784)


## ENVIRONMENT VARIABLES

- spring.application.name=notes
- spring.datasource.url
- spring.datasource.username
- spring.datasource.password
- spring.datasource.driver-class-name= com.mysql.cj.jdbc.Driver
- spring.jpa.hibernate.ddl-auto=update
- sever.servlet.context-path=/api
- app.jwt.secret
- app.salt
- app.jwtExpirationMs

All the environment variables are stored in the `.env` file and all variables are required for the application to run.

Kindly create a `.env` file in the root directory of the project and add the above environment variables.

