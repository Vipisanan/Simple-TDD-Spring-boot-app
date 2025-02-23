
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

### **Step 2: Repository Layer Unit Test Documentation**

## Annotations Used

### `@DataJpaTest`

- **Purpose:** This annotation is used to test JPA repositories in isolation. It configures an in-memory database (H2 by default) and scans for `@Entity` classes, setting up only the necessary JPA components.
- **Why Used Here?** Since the repository layer interacts with the database, `@DataJpaTest` provides a lightweight way to test it without loading the entire application context.
- **Alternative:** If you need to test the repository along with service layers or other dependencies, you can use `@SpringBootTest` instead.

### `@Autowired`

- **Purpose:** Injects the `TaskRepository` bean into the test class so that we can perform repository operations.
- **Why Used Here?** It ensures that the repository instance is available without explicitly instantiating it.
- **Alternative:** Instead of `@Autowired`, you could use constructor injection or manually instantiate the repository using `new` if dependencies allow.

### `@Test`

- **Purpose:** Marks a method as a test case to be executed by JUnit.
- **Why Used Here?** It helps JUnit recognize which methods are test cases.
- **Alternative:** There is no real alternative in JUnit 5, but in JUnit 4, you would use `@org.junit.Test`.

### **Step 3: Service Layer Unit Test Documentation**

## Annotations Used

### `@ExtendWith(MockitoExtension.class)`

- **Purpose:** Enables Mockito support in JUnit 5 tests by integrating Mockito’s functionality.
- **Why Used Here?** This ensures that Mockito can automatically initialize mocks and inject them where needed.
- **Alternative:** Instead of using `@ExtendWith(MockitoExtension.class)`, you could manually initialize mocks using `MockitoAnnotations.initMocks(this);` inside a `@BeforeEach` method.

### `@Mock`

- **Purpose:** Creates a mock instance of `TaskRepository`, which is injected into the service being tested.
- **Why Used Here?** Mocking dependencies ensures that the test focuses only on the `TaskService` behavior and does not rely on database interactions.
- **Alternative:** Instead of `@Mock`, you could manually create a mock using `mock(TaskRepository.class)`.

### `@InjectMocks`

- **Purpose:** Injects mocked dependencies into the `TaskService` instance.
- **Why Used Here?** This ensures that the service class is tested with its dependencies mocked, isolating its logic from external factors.
- **Alternative:** Manually injecting mocks using a constructor or setter methods.

### `@Test`

- **Purpose:** Marks a method as a test case to be executed by JUnit.
- **Why Used Here?** It helps JUnit recognize which methods are test cases.
- **Alternative:** In JUnit 4, you would use `@org.junit.Test`.