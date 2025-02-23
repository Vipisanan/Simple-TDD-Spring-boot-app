
# **Test-Driven Development (TDD) in Spring Boot with JUnit and Mockito**

### **Step 1: Create a New Spring Boot Project**

Go to [Spring Initializr](https://start.spring.io/) and generate a new Spring Boot project with the following dependencies:

- **`spring-boot-starter-test`** → Provides JUnit, Mockito, and other testing utilities.

  > **Spring Web** alone does not provide **`spring-boot-starter-test`** automatically. However, **Spring Boot itself includes it by default for testing purposes**, even if it's not explicitly listed in **Spring Initializr**.
>
- **`H2 Database`** → In-memory database for testing.
- **`Lombok`** → Reduces boilerplate code (e.g., getters, setters, constructors).
- **`Spring Web`** → Required for building REST APIs.
- **`Spring Data JPA`** → ORM for database interactions.

Once configured, generate and download the project, then extract it and open it in your preferred IDE (e.g., IntelliJ IDEA, VS Code, Eclipse).