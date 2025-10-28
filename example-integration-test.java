// Ejemplo de cómo usar TestContainers para tests de integración
@Testcontainers
class UserServiceIntegrationTest {
    
    @Container
    static GenericContainer<?> eureka = new GenericContainer<>("selimhorri/service-discovery-ecommerce-boot:0.1.0")
            .withExposedPorts(8761)
            .waitingFor(Wait.forHttp("/eureka/apps").forStatusCode(200));
    
    @Container
    static GenericContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("ecommerce")
            .withUsername("root")
            .withPassword("password");
    
    @Test
    void testUserServiceIntegration() {
        // Los contenedores se levantan automáticamente
        // Los tests se ejecutan contra estos contenedores
        // Se limpian automáticamente al final
    }
}
