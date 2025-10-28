package com.selimhorri.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
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

import com.selimhorri.app.domain.Address;
import com.selimhorri.app.domain.Credential;
import com.selimhorri.app.domain.RoleBasedAuthority;
import com.selimhorri.app.domain.User;
import com.selimhorri.app.dto.UserDto;
import com.selimhorri.app.exception.wrapper.UserObjectNotFoundException;
import com.selimhorri.app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	private com.selimhorri.app.service.impl.UserServiceImpl userService;
	
	private User testUser;
	private UserDto testUserDto;
	
	@BeforeEach
	void setUp() {
		userService = new com.selimhorri.app.service.impl.UserServiceImpl(userRepository);
		
		testUser = new User();
		testUser.setUserId(1);
		testUser.setFirstName("Test");
		testUser.setLastName("User");
		testUser.setEmail("test@example.com");
		testUser.setImageUrl("https://example.com/image.jpg");
		
		testUserDto = new UserDto();
		testUserDto.setUserId(1);
		testUserDto.setFirstName("Test");
		testUserDto.setLastName("User");
		testUserDto.setEmail("test@example.com");
	}
	
	@Test
	void testFindAll() {
		// Arrange
		when(userRepository.findAll()).thenReturn(Arrays.asList(testUser));
		
		// Act
		List<UserDto> result = userService.findAll();
		
		// Assert
		assertNotNull(result);
		assertEquals(1, result.size());
		verify(userRepository, times(1)).findAll();
	}
	
	@Test
	void testFindById() {
		// Arrange
		when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
		
		// Act
		UserDto result = userService.findById(1);
		
		// Assert
		assertNotNull(result);
		assertEquals(1, result.getUserId());
		assertEquals("Test", result.getFirstName());
		verify(userRepository, times(1)).findById(1);
	}
	
	@Test
	void testFindByIdNotFound() {
		// Arrange
		when(userRepository.findById(999)).thenReturn(Optional.empty());
		
		// Act & Assert
		assertThrows(UserObjectNotFoundException.class, () -> {
			userService.findById(999);
		});
		verify(userRepository, times(1)).findById(999);
	}
	
	@Test
	void testSave() {
		// Arrange
		when(userRepository.save(any(User.class))).thenReturn(testUser);
		
		// Act
		UserDto result = userService.save(testUserDto);
		
		// Assert
		assertNotNull(result);
		assertEquals(1, result.getUserId());
		verify(userRepository, times(1)).save(any(User.class));
	}
	
	@Test
	void testDeleteById() {
		// Arrange
		when(userRepository.existsById(1)).thenReturn(true);
		
		// Act
		userService.deleteById(1);
		
		// Assert
		verify(userRepository, times(1)).deleteById(1);
	}
}

