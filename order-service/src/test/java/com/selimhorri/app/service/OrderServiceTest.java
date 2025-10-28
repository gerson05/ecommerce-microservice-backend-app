package com.selimhorri.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.selimhorri.app.domain.Order;
import com.selimhorri.app.domain.OrderStatus;
import com.selimhorri.app.dto.OrderDto;
import com.selimhorri.app.exception.wrapper.OrderObjectNotFoundException;
import com.selimhorri.app.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
	
	@Mock
	private OrderRepository orderRepository;
	
	@InjectMocks
	private com.selimhorri.app.service.impl.OrderServiceImpl orderService;
	
	private Order testOrder;
	private OrderDto testOrderDto;
	
	@BeforeEach
	void setUp() {
		testOrder = new Order();
		testOrder.setOrderId(1);
		testOrder.setOrderTrackingNumber("ORD-001");
		testOrder.setOrderDate(LocalDateTime.now());
		testOrder.setOrderTotal(BigDecimal.valueOf(100.0));
		testOrder.setOrderStatus(OrderStatus.PENDING);
		
		testOrderDto = new OrderDto();
		testOrderDto.setOrderId(1);
		testOrderDto.setOrderTrackingNumber("ORD-001");
		testOrderDto.setOrderTotal(BigDecimal.valueOf(100.0));
	}
	
	@Test
	void testFindAll() {
		// Arrange
		when(orderRepository.findAll()).thenReturn(Arrays.asList(testOrder));
		
		// Act
		List<OrderDto> result = orderService.findAll();
		
		// Assert
		assertNotNull(result);
		assertEquals(1, result.size());
		verify(orderRepository, times(1)).findAll();
	}
	
	@Test
	void testFindById() {
		// Arrange
		when(orderRepository.findById(1)).thenReturn(Optional.of(testOrder));
		
		// Act
		OrderDto result = orderService.findById(1);
		
		// Assert
		assertNotNull(result);
		assertEquals(1, result.getOrderId());
		assertEquals("ORD-001", result.getOrderTrackingNumber());
		verify(orderRepository, times(1)).findById(1);
	}
	
	@Test
	void testFindByIdNotFound() {
		// Arrange
		when(orderRepository.findById(999)).thenReturn(Optional.empty());
		
		// Act & Assert
		assertThrows(OrderObjectNotFoundException.class, () -> {
			orderService.findById(999);
		});
		verify(orderRepository, times(1)).findById(999);
	}
	
	@Test
	void testSave() {
		// Arrange
		when(orderRepository.save(any(Order.class))).thenReturn(testOrder);
		
		// Act
		OrderDto result = orderService.save(testOrderDto);
		
		// Assert
		assertNotNull(result);
		assertEquals(1, result.getOrderId());
		verify(orderRepository, times(1)).save(any(Order.class));
	}
	
	@Test
	void testUpdateStatus() {
		// Arrange
		testOrder.setOrderStatus(OrderStatus.COMPLETED);
		when(orderRepository.findById(1)).thenReturn(Optional.of(testOrder));
		when(orderRepository.save(any(Order.class))).thenReturn(testOrder);
		
		// Act
		testOrderDto.setOrderStatus("COMPLETED");
		OrderDto result = orderService.update(testOrderDto);
		
		// Assert
		assertNotNull(result);
		assertEquals(1, result.getOrderId());
		verify(orderRepository, times(1)).save(any(Order.class));
	}
}

