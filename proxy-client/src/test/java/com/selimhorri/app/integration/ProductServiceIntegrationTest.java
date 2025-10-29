package com.selimhorri.app.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	properties = {
		"eureka.client.register-with-eureka=false",
		"eureka.client.fetch-registry=false",
		"spring.cloud.discovery.enabled=false",
		"spring.profiles.active=test",
		"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration"
	}
)
class ProductServiceIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	private String getBaseUrl() {
		return "http://localhost:" + port + "/app/api/products";
	}
	
	@Test
	void testGetAllProducts() {
		// Arrange
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		// Act
		ResponseEntity<String> response = restTemplate.exchange(
			getBaseUrl(), 
			HttpMethod.GET, 
			entity, 
			String.class
		);
		
		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}
	
	@Test
	void testGetProductById() {
		// Arrange
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		// Act
		ResponseEntity<String> response = restTemplate.exchange(
			getBaseUrl() + "/1", 
			HttpMethod.GET, 
			entity, 
			String.class
		);
		
		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}
	
	@Test
	void testGetProductsByCategory() {
		// Arrange
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		// Act
		ResponseEntity<String> response = restTemplate.exchange(
			getBaseUrl() + "/category/1", 
			HttpMethod.GET, 
			entity, 
			String.class
		);
		
		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}
}

