package com.shopping.cart.controller.ext;

import com.shopping.cart.model.dto.ProductDTO;
import com.shopping.cart.model.dto.UserDTO;
import com.shopping.cart.service.ext.ProductService;
import com.shopping.cart.service.ext.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ExtControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ExtController extController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mockListProducts() {
        List<ProductDTO> products = createProductDTO();
        when(productService.getAll()).thenReturn(products);

        ResponseEntity<List<ProductDTO>> response = extController.listProducts();

        assertEquals(products, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void mockListUsers() {
        List<UserDTO> users = createUserDTO();
        when(userService.getAll()).thenReturn(users);

        ResponseEntity<List<UserDTO>> response = extController.listUsers();

        assertEquals(users, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private List<ProductDTO> createProductDTO() {
        List<ProductDTO> productDTOS = new ArrayList<>();
        ProductDTO p1 = new ProductDTO();
        p1.setId(1L);
        p1.setPrice(new BigDecimal("399.00"));
        p1.setBrand("Nike");
        p1.setName("Running Shoes");
        p1.setProdType("Comfortable Running Shoes");
        p1.setProdInfo("Nice shoes for runner");
        productDTOS.add(p1);

        ProductDTO p2 = new ProductDTO();
        p2.setId(2L);
        p2.setPrice(new BigDecimal("699.00"));
        p2.setBrand("Apple");
        p2.setName("Iphone 13");
        p2.setProdType("Electronics");
        p2.setProdInfo("Latest smartphone with cutting-edge featuresr");
        productDTOS.add(p2);

        return productDTOS;
    }

    private List<UserDTO> createUserDTO() {
        List<UserDTO> userDTOS = new ArrayList<>();
        UserDTO p1 = new UserDTO();
        p1.setEmail("rahul.singh@gmail.com");
        p1.setMobile("07234567890");
        p1.setName("Rahul");
        userDTOS.add(p1);

        UserDTO p2 = new UserDTO();
        p2.setEmail("sonal.singh@yahoo.com");
        p2.setMobile("09876543213");
        p2.setName("Sonal");
        userDTOS.add(p2);

        return userDTOS;
    }
}