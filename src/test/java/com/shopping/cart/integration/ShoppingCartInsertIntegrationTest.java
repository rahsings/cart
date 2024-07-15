package com.shopping.cart.integration;

import com.shopping.cart.exception.InvalidInputException;
import com.shopping.cart.exception.ProductNotFoundException;
import com.shopping.cart.exception.UserNotFoundException;
import com.shopping.cart.mapper.ShoppingCartMapper;
import com.shopping.cart.model.dto.CartItemDTO;
import com.shopping.cart.model.response.CartResponse;
import com.shopping.cart.model.entity.CartItem;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"
})
public class ShoppingCartInsertIntegrationTest {

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
    public void test_AddItemToCart() {
        Long userId = 1L;
        Long productId = 1L;
        int quantity = 2;

        CartRequestInsert cartRequestInsert = new CartRequestInsert();
        cartRequestInsert.setUserId(userId);
        cartRequestInsert.setProductId(productId);
        cartRequestInsert.setQuantity(quantity);

        validationService.validateObject(CartRequestInsert.class, cartRequestInsert);
        CartResponse result = shoppingCartService.addItemToCart(cartRequestInsert);

        assertNotNull(result);
        assertEquals(userId, result.getUser().getId());
        Integer quantityRes = result.getCartItems().stream().filter(cartItemDTO -> cartItemDTO.getProduct().getId().equals(productId))
                .map(CartItemDTO::getQuantity).findFirst().get();
        assertNotNull(quantityRes);
        assertEquals(quantityRes, getActualFromDb(1L, productId));
    }

    private Integer getActualFromDb(long userId, Long productId) {
        return shoppingCartRepository.findByUser(userId).get().getCartItems().stream().filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .map(CartItem::getQuantity).findFirst().get();
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void test_FailValidation_AddItemToCart() {
        Long userId = 1L;
        Long productId = null;
        int quantity = 2;

        CartRequestInsert cartRequestInsert = new CartRequestInsert();
        cartRequestInsert.setUserId(userId);
        cartRequestInsert.setProductId(productId);
        cartRequestInsert.setQuantity(quantity);

        assertThrows(InvalidInputException.class, () -> {
            validationService.validateObject(CartRequestInsert.class, cartRequestInsert);
            shoppingCartService.addItemToCart(cartRequestInsert);
        });
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void test_FailUserNotFound_AddItemToCart() {
        Long userId = 20L;
        Long productId = 1L;
        int quantity = 2;

        CartRequestInsert cartRequestInsert = new CartRequestInsert();
        cartRequestInsert.setUserId(userId);
        cartRequestInsert.setProductId(productId);
        cartRequestInsert.setQuantity(quantity);

        validationService.validateObject(CartRequestInsert.class, cartRequestInsert);
        assertThrows(UserNotFoundException.class, () -> shoppingCartService.addItemToCart(cartRequestInsert));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void test_FailProductNotFound_AddItemToCart() {
        Long userId = 1L;
        Long productId = 10L;
        int quantity = 2;

        CartRequestInsert cartRequestInsert = new CartRequestInsert();
        cartRequestInsert.setUserId(userId);
        cartRequestInsert.setProductId(productId);
        cartRequestInsert.setQuantity(quantity);

        validationService.validateObject(CartRequestInsert.class, cartRequestInsert);
        assertThrows(ProductNotFoundException.class, () -> shoppingCartService.addItemToCart(cartRequestInsert));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void test_AddQuantityTwice_AddItemToCart() {
        Long userId = 1L;
        Long productId = 1L;
        int quantity = 2;

        CartRequestInsert cartRequestInsert = new CartRequestInsert();
        cartRequestInsert.setUserId(userId);
        cartRequestInsert.setProductId(productId);
        cartRequestInsert.setQuantity(quantity);

        validationService.validateObject(CartRequestInsert.class, cartRequestInsert);
        CartResponse result = shoppingCartService.addItemToCart(cartRequestInsert);
        assertTrue(result.getCartItems().stream().anyMatch(item -> item.getProduct().getId().equals(productId) && item.getQuantity() == quantity));

        CartResponse result2 = shoppingCartService.addItemToCart(cartRequestInsert);
        assertTrue(result2.getCartItems().stream().anyMatch(item -> item.getProduct().getId().equals(productId) && item.getQuantity() == quantity * 2));
    }
}
