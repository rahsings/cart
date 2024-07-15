package com.shopping.cart.integration;

import com.shopping.cart.exception.CartNotFoundException;
import com.shopping.cart.exception.InvalidInputException;
import com.shopping.cart.mapper.ShoppingCartMapper;
import com.shopping.cart.model.response.CartResponse;
import com.shopping.cart.model.request.CartRequestInsert;
import com.shopping.cart.model.response.CartResponseTotal;
import com.shopping.cart.repository.CartItemRepository;
import com.shopping.cart.repository.ProductRepository;
import com.shopping.cart.repository.ShoppingCartRepository;
import com.shopping.cart.service.CartService;
import com.shopping.cart.service.ShoppingCartRepositoryService;
import com.shopping.cart.service.ShoppingCartService;
import com.shopping.cart.service.ValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"
})
public class ShoppingCartGetAndTotalIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ShoppingCartRepositoryService shoppingCartRepositoryService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private ValidationService validationService;

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test_GetItem_Valid() {

        Long userId = 1L;
        Long productId = 1L;
        int quantity = 2;

        CartRequestInsert cartRequestInsert = new CartRequestInsert();
        cartRequestInsert.setUserId(userId);
        cartRequestInsert.setProductId(productId);
        cartRequestInsert.setQuantity(quantity);
        validationService.validateObject(CartRequestInsert.class, cartRequestInsert);
        shoppingCartService.addItemToCart(cartRequestInsert);

        Long cartId = 1L;

        validationService.validateObject(Long.class, cartId);
        CartResponse result1 = shoppingCartService.getCart(cartId);

        assertNotNull(result1);
        assertEquals(cartId, result1.getId());
        assertEquals(result1.getCartItems().size(), 1);
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test_GetItem_Invalid() {

        Long cartId = 1L;

        validationService.validateObject(Long.class, cartId);
        assertThrows(CartNotFoundException.class, () -> shoppingCartService.getCart(cartId));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test_GetItem_Invalid_Input() {

        Long cartId = null;

        assertThrows(InvalidInputException.class, () -> {
            validationService.validateObject(Long.class, cartId);
            shoppingCartService.getCart(cartId);
        });
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test_GetTotal_Valid() {

        Long userId = 1L;
        Long productId = 1L;
        int quantity = 2;

        CartRequestInsert cartRequestInsert = new CartRequestInsert();
        cartRequestInsert.setUserId(userId);
        cartRequestInsert.setProductId(productId);
        cartRequestInsert.setQuantity(quantity);
        validationService.validateObject(CartRequestInsert.class, cartRequestInsert);
        shoppingCartService.addItemToCart(cartRequestInsert);

        Long cartId = 1L;

        validationService.validateObject(Long.class, cartId);
        CartResponseTotal result1 = shoppingCartService.getCartTotal(cartId);

        assertNotNull(result1);
        assertEquals(cartId, result1.getId());
        assertEquals(result1.getCartItems().size(), 1);
        assertEquals(result1.getTotal().doubleValue(), 699.99 * 2);
    }
}
