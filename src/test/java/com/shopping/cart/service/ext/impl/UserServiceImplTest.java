package com.shopping.cart.service.ext.impl;

import com.shopping.cart.exception.UserNotFoundException;
import com.shopping.cart.mapper.UserMapper;
import com.shopping.cart.model.dto.UserDTO;
import com.shopping.cart.model.entity.User;
import com.shopping.cart.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = createUser();
        userDTO = createUserDTO();
    }

    @Test
    void test_GetUserFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.get(1L);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        verify(userRepository).findById(1L);
    }

    @Test
    void test_GetUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.get(1L));

        assertEquals("Invalid! User not found for given USER_ID: 1", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void test_GetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(userMapper.userToUserDTO(Collections.singletonList(user))).thenReturn(Collections.singletonList(userDTO));

        List<UserDTO> result = userService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userDTO.getId(), result.get(0).getId());
        verify(userRepository).findAll();
    }

    @Test
    void test_EvictCacheUsers() {
        userService.evictCacheUsers();
        verify(userRepository, never()).findAll();
    }

    @Test
    void test_EvictCacheForID() {
        userService.evictCacheForID(1L);
        verify(userRepository, never()).findById(anyLong());
    }

    private UserDTO createUserDTO() {
        UserDTO p1 = new UserDTO();
        p1.setEmail("rahul.singh@gmail.com");
        p1.setMobile("07234567890");
        p1.setName("Rahul");
        return p1;
    }

    private User createUser(){
        User p2 = new User();
        p2.setEmail("sonal.singh@yahoo.com");
        p2.setMobile("09876543213");
        p2.setName("Sonal");

        return p2;
    }
}

