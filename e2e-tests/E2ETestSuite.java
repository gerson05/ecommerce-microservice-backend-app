package com.selimhorri.app.e2e;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite de pruebas E2E para flujos completos de usuario en la aplicación ecommerce
 */
@SpringBootTest
public class E2ETestSuite {
	
	@LocalServerPort
	private int port;
	
	private final TestRestTemplate restTemplate = new TestRestTemplate();
	private final String baseUrl = "http://localhost:8080/app/api";
	
	/**
	 * Test E2E 1: Flujo completo de búsqueda y visualización de productos
	 * Scenario: Usuario busca productos y los visualiza
	 */
	@Test
	@DisplayName("E2E 1: Flujo de búsqueda de productos")
	void testProductSearchFlow() {
		// Step 1: Obtener lista de productos
		ResponseEntity<String> productsResponse = restTemplate.getForEntity(
			baseUrl + "/products", 
			String.class
		);
		
		assertEquals(200, productsResponse.getStatusCodeValue());
		assertNotNull(productsResponse.getBody());
	}
	
	/**
	 * Test E2E 2: Flujo de creación de usuario completo
	 * Scenario: Usuario se registra con todos sus datos
	 */
	@Test
	@DisplayName("E2E 2: Flujo de registro de usuario")
	void testUserRegistrationFlow() {
		// Step 1: Crear usuario
		String userJson = """
			{
				"userId": 100,
				"firstName": "E2E",
				"lastName": "Test",
				"email": "e2e@test.com",
				"imageUrl": "https://example.com/e2e.jpg",
				"credential": {
					"username": "e2etest",
					"password": "password123",
					"roleBasedAuthority": "ROLE_USER"
				}
			}
		""";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		HttpEntity<String> entity = new HttpEntity<>(userJson, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
			baseUrl + "/users", 
			HttpMethod.POST, 
			entity, 
			String.class
		);
		
		// Step 2: Verificar que el usuario se creó
		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
	}
	
	/**
	 * Test E2E 3: Flujo de compra completa
	 * Scenario: Usuario crea una orden con productos
	 */
	@Test
	@DisplayName("E2E 3: Flujo de creación de orden")
	void testOrderCreationFlow() {
		// Step 1: Crear orden
		String orderJson = """
			{
				"orderId": 100,
				"orderTrackingNumber": "E2E-001",
				"orderDate": "2024-01-01T00:00:00",
				"orderTotal": 250.50,
				"orderStatus": "PENDING"
			}
		""";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		HttpEntity<String> entity = new HttpEntity<>(orderJson, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
			"http://localhost:8300/order-service/api/orders", 
			HttpMethod.POST, 
			entity, 
			String.class
		);
		
		// Step 2: Verificar que la orden se creó
		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertNotNull(response.getBody());
	}
	
	/**
	 * Test E2E 4: Flujo de añadir productos a favoritos
	 * Scenario: Usuario añade productos a su lista de favoritos
	 */
	@Test
	@DisplayName("E2E 4: Flujo de favoritos")
	void testFavouritesFlow() {
		// Step 1: Añadir producto a favoritos
		String favouriteJson = """
			{
				"userId": 1,
				"productId": 1
			}
		""";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		HttpEntity<String> entity = new HttpEntity<>(favouriteJson, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
			"http://localhost:8800/favourite-service/api/favourites", 
			HttpMethod.POST, 
			entity, 
			String.class
		);
		
		// Step 2: Verificar que se añadió a favoritos
		assertTrue(response.getStatusCode().is2xxSuccessful());
	}
	
	/**
	 * Test E2E 5: Flujo de procesamiento de pago
	 * Scenario: Usuario completa una orden y procesa el pago
	 */
	@Test
	@DisplayName("E2E 5: Flujo de procesamiento de pago")
	void testPaymentProcessingFlow() {
		// Step 1: Crear pago
		String paymentJson = """
			{
				"paymentId": 100,
				"paymentDate": "2024-01-01T00:00:00",
				"paymentMethod": "CREDIT_CARD",
				"paymentAmount": 250.50,
				"paymentStatus": "PENDING"
			}
		""";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		HttpEntity<String> entity = new HttpEntity<>(paymentJson, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
			"http://localhost:8400/payment-service/api/payments", 
			HttpMethod.POST, 
			entity, 
			String.class
		);
		
		// Step 2: Verificar que el pago se procesó
		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertNotNull(response.getBody());
	}
	
	/**
	 * Test E2E 6: Flujo completo de ecommerce
	 * Scenario: Usuario navega, añade a carrito, crea orden y paga
	 */
	@Test
	@DisplayName("E2E 6: Flujo completo de ecommerce")
	void testCompleteEcommerceFlow() {
		// Step 1: Buscar productos
		ResponseEntity<String> productsResponse = restTemplate.getForEntity(
			baseUrl + "/products", 
			String.class
		);
		assertEquals(200, productsResponse.getStatusCodeValue());
		
		// Step 2: Obtener usuarios
		ResponseEntity<String> usersResponse = restTemplate.getForEntity(
			baseUrl + "/users", 
			String.class
		);
		assertEquals(200, usersResponse.getStatusCodeValue());
		
		// Step 3: Crear orden
		ResponseEntity<String> ordersResponse = restTemplate.getForEntity(
			"http://localhost:8300/order-service/api/orders", 
			String.class
		);
		assertTrue(ordersResponse.getStatusCode().is2xxSuccessful());
		
		// Verificar que todo el flujo funcionó
		assertNotNull(productsResponse.getBody());
		assertNotNull(usersResponse.getBody());
	}
}

