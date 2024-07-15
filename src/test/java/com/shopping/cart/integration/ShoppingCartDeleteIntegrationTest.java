package com.shopping.cart.integration;

import com.shopping.cart.exception.CartNotFoundException;
import com.shopping.cart.mapper.ShoppingCartMapper;
import com.shopping.cart.model.dto.ShoppingCartDTO;
import com.shopping.cart.model.request.CartRequestDelete;
import com.shopping.cart.model.request.CartRequestInsert;
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
public class ShoppingCartDeleteIntegrationTest {

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
    void test_DeleteItem_Quantity_Positive() {

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
        Long item1 = 1L;

        CartRequestDelete cartRequestDelete = new CartRequestDelete();
        cartRequestDelete.setItemId(item1);
        cartRequestDelete.setCartId(cartId);

        validationService.validateObject(CartRequestDelete.class, cartRequestDelete);
        ShoppingCartDTO result1 = shoppingCartService.removeItemFromCart(cartRequestDelete);

        assertNotNull(result1);
        assertEquals(cartId, result1.getId());
        assertTrue(result1.getCartItems().isEmpty());
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test_DeleteItem_NotFound() {

        Long userId = 1L;
        Long productId = 1L;
        int quantity = 2;

        CartRequestInsert cartRequest = new CartRequestInsert();
        cartRequest.setUserId(userId);
        cartRequest.setProductId(productId);
        cartRequest.setQuantity(quantity);
        validationService.validateObject(CartRequestInsert.class, cartRequest);
        shoppingCartService.addItemToCart(cartRequest);

        Long cartId = 1L;
        Long item1 = 2L;

        CartRequestDelete cartRequestDelete = new CartRequestDelete();
        cartRequestDelete.setItemId(item1);
        cartRequestDelete.setCartId(cartId);

        validationService.validateObject(CartRequestDelete.class, cartRequestDelete);
        assertThrows(CartNotFoundException.class, () -> shoppingCartService.removeItemFromCart(cartRequestDelete));

        Long productId1 = 1L;
        int quantity2 = 2;

        cartRequest.setProductId(productId1);
        cartRequest.setQuantity(quantity2);
        validationService.validateObject(CartRequestInsert.class, cartRequest);
        ShoppingCartDTO res = shoppingCartService.addItemToCart(cartRequest);
        assertEquals(res.getCartItems().size(), 1);
    }
}
