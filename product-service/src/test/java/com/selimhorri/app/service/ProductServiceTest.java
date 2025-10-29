package com.selimhorri.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.selimhorri.app.domain.Category;
import com.selimhorri.app.domain.Product;
import com.selimhorri.app.dto.CategoryDto;
import com.selimhorri.app.dto.ProductDto;
import com.selimhorri.app.exception.wrapper.ProductNotFoundException;
import com.selimhorri.app.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
	
	@Mock
	private ProductRepository productRepository;
	
	private com.selimhorri.app.service.impl.ProductServiceImpl productService;
	
	private Product testProduct;
	private ProductDto testProductDto;
	
	@BeforeEach
	void setUp() {
		productService = new com.selimhorri.app.service.impl.ProductServiceImpl(productRepository);
		
		Category category = new Category();
		category.setCategoryId(1);
		category.setCategoryTitle("Electronics");
		
		testProduct = new Product();
		testProduct.setProductId(1);
		testProduct.setProductTitle("Test Product");
		testProduct.setImageUrl("https://example.com/product.jpg");
		testProduct.setSku("TEST-SKU-001");
		testProduct.setPriceUnit(100.0);
		testProduct.setQuantity(50);
		testProduct.setCategory(category);
		
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setCategoryId(1);
		categoryDto.setCategoryTitle("Electronics");
		
		testProductDto = new ProductDto();
		testProductDto.setProductId(1);
		testProductDto.setProductTitle("Test Product");
		testProductDto.setPriceUnit(100.0);
		testProductDto.setCategoryDto(categoryDto);
	}
	
	@Test
	void testFindAll() {
		// Arrange
		when(productRepository.findAll()).thenReturn(Arrays.asList(testProduct));
		
		// Act
		List<ProductDto> result = productService.findAll();
		
		// Assert
		assertNotNull(result);
		assertEquals(1, result.size());
		verify(productRepository, times(1)).findAll();
	}
	
	@Test
	void testFindById() {
		// Arrange
		when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
		
		// Act
		ProductDto result = productService.findById(1);
		
		// Assert
		assertNotNull(result);
		assertEquals(1, result.getProductId());
		assertEquals("Test Product", result.getProductTitle());
		verify(productRepository, times(1)).findById(1);
	}
	
	@Test
	void testFindByIdNotFound() {
		// Arrange
		when(productRepository.findById(999)).thenReturn(Optional.empty());
		
		// Act & Assert
		assertThrows(ProductNotFoundException.class, () -> {
			productService.findById(999);
		});
		verify(productRepository, times(1)).findById(999);
	}
	
	@Test
	void testSave() {
		// Arrange
		when(productRepository.save(any(Product.class))).thenReturn(testProduct);
		
		// Act
		ProductDto result = productService.save(testProductDto);
		
		// Assert
		assertNotNull(result);
		assertEquals(1, result.getProductId());
		verify(productRepository, times(1)).save(any(Product.class));
	}
	
	@Test
	void testUpdate() {
		// Arrange
		when(productRepository.save(any(Product.class))).thenReturn(testProduct);
		
		// Act
		ProductDto result = productService.update(testProductDto);
		
		// Assert
		assertNotNull(result);
		assertEquals(1, result.getProductId());
		verify(productRepository, times(1)).save(any(Product.class));
	}
}

