package com.shopping.cart.integration;

import com.shopping.cart.exception.CartValueInSufficientException;
import com.shopping.cart.mapper.ShoppingCartMapper;
import com.shopping.cart.model.response.CartResponse;
import com.shopping.cart.model.request.CartRequestInsert;
import com.shopping.cart.model.request.CartRequestUpdate;
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

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"
})
public class ShoppingCartUpdateIntegrationTest {

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
    void test_UpdateItem_Quantity_Positive() {

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
        int newQuantity = 4;
        int prevQuantity = 2;
        Long item1 = 1L;

        CartRequestUpdate cartRequestUpdate = new CartRequestUpdate();
        cartRequestUpdate.setQuantity(newQuantity);
        cartRequestUpdate.setItemId(item1);
        cartRequestUpdate.setCartId(cartId);

        validationService.validateObject(CartRequestUpdate.class, cartRequestUpdate);
        CartResponse result1 = shoppingCartService.updateItemQuantity(cartRequestUpdate);

        assertNotNull(result1);
        assertEquals(cartId, result1.getId());
        assertTrue(result1.getCartItems().stream().anyMatch(item -> Objects.equals(item.getId(), item1) && item.getQuantity() == (newQuantity + prevQuantity)));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test_UpdateItem_Quantity_Negative() {

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
        int newQuantity = -2;
        Long item1 = 1L;

        CartRequestUpdate cartRequestUpdate = new CartRequestUpdate();
        cartRequestUpdate.setQuantity(newQuantity);
        cartRequestUpdate.setItemId(item1);
        cartRequestUpdate.setCartId(cartId);

        validationService.validateObject(CartRequestUpdate.class, cartRequestUpdate);
        CartResponse result1 = shoppingCartService.updateItemQuantity(cartRequestUpdate);

        assertNotNull(result1);
        assertEquals(cartId, result1.getId());
        assertTrue(result1.getCartItems().isEmpty());

        Long productId1 = 2L;
        int quantity2 = 2;

        cartRequestInsert.setProductId(productId1);
        cartRequestInsert.setQuantity(quantity2);
        validationService.validateObject(CartRequestInsert.class, cartRequestInsert);
        CartResponse res = shoppingCartService.addItemToCart(cartRequestInsert);
        assertEquals(res.getCartItems().size(), 1);
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test_UpdateItem_Quantity_Negative_Return1() {

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
        int newQuantity = -1;
        Long item1 = 1L;

        CartRequestUpdate cartRequestUpdate = new CartRequestUpdate();
        cartRequestUpdate.setQuantity(newQuantity);
        cartRequestUpdate.setItemId(item1);
        cartRequestUpdate.setCartId(cartId);

        validationService.validateObject(CartRequestUpdate.class, cartRequestUpdate);
        CartResponse result1 = shoppingCartService.updateItemQuantity(cartRequestUpdate);

        assertNotNull(result1);
        assertEquals(cartId, result1.getId());
        assertTrue(result1.getCartItems().stream().anyMatch(item -> Objects.equals(item.getId(), item1) && item.getQuantity() == 1));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test_UpdateItem_Quantity_Negative_High_Value_ThrowsException() {

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
        int newQuantity = -5;
        Long item1 = 1L;

        CartRequestUpdate cartRequestUpdate = new CartRequestUpdate();
        cartRequestUpdate.setQuantity(newQuantity);
        cartRequestUpdate.setCartId(cartId);
        cartRequestUpdate.setItemId(item1);

        validationService.validateObject(CartRequestUpdate.class, cartRequestUpdate);
        assertThrows(CartValueInSufficientException.class, () -> shoppingCartService.updateItemQuantity(cartRequestUpdate));
    }
}
