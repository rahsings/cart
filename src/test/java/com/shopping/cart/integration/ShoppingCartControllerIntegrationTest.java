package com.shopping.cart.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.cart.mapper.ShoppingCartMapper;
import com.shopping.cart.model.request.CartRequestDelete;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"
})
@AutoConfigureMockMvc
public class ShoppingCartControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    void add_ItemToCartTest_Success() throws Exception {

        Long userId = 1L;
        Long productId = 1L;
        int quantity = 2;

        CartRequestInsert cartRequestInsert = new CartRequestInsert();
        cartRequestInsert.setUserId(userId);
        cartRequestInsert.setProductId(productId);
        cartRequestInsert.setQuantity(quantity);

        mockMvc.perform(post("/api/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequestInsert)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(1L))
                .andExpect(jsonPath("$.user.addresses").isNotEmpty())
                .andExpect(jsonPath("$.cartItems").isArray());
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void add_ItemToCartTest_Failure() throws Exception {

        Long userId = 1L;
        Long productId = null;
        int quantity = 2;

        CartRequestInsert cartRequestInsert = new CartRequestInsert();
        cartRequestInsert.setUserId(userId);
        cartRequestInsert.setProductId(productId);
        cartRequestInsert.setQuantity(quantity);

        mockMvc.perform(post("/api/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequestInsert)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void update_ItemQuantityTest_Success() throws Exception {

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
        Long itemId = 1L;
        int quantity_new = 2;

        CartRequestUpdate cartRequestUpdate = new CartRequestUpdate();
        cartRequestUpdate.setCartId(cartId);
        cartRequestUpdate.setItemId(itemId);
        cartRequestUpdate.setQuantity(quantity_new);


        mockMvc.perform(put("/api/v1/cart/{cartId}", cartId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequestUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(1L))
                .andExpect(jsonPath("$.user.addresses").isNotEmpty())
                .andExpect(jsonPath("$.cartItems").isArray())
                .andExpect(jsonPath("$.cartItems.[0].quantity").value(4))
                .andExpect(jsonPath("$.cartItems.[0].id").value(cartId));
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void update_ItemQuantityTest_Empty_Item() throws Exception {

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
        Long itemId = 1L;
        int quantity_new = -2;

        CartRequestUpdate cartRequestUpdate = new CartRequestUpdate();
        cartRequestUpdate.setCartId(cartId);
        cartRequestUpdate.setItemId(itemId);
        cartRequestUpdate.setQuantity(quantity_new);


        mockMvc.perform(put("/api/v1/cart/{cartId}", cartId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequestUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(1L))
                .andExpect(jsonPath("$.user.addresses").isNotEmpty())
                .andExpect(jsonPath("$.cartItems").isArray())
                .andExpect(jsonPath("$.cartItems").isEmpty());
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void update_ItemQuantityTest_Failure() throws Exception {

        Long userId = 1L;
        Long productId = 1L;
        int quantity = 2;

        CartRequestInsert cartRequestInsert = new CartRequestInsert();
        cartRequestInsert.setUserId(userId);
        cartRequestInsert.setProductId(productId);
        cartRequestInsert.setQuantity(quantity);
        validationService.validateObject(CartRequestInsert.class, cartRequestInsert);
        shoppingCartService.addItemToCart(cartRequestInsert);

        Long cartId = 2L;
        Long itemId = null;
        int quantity_new = -2;

        CartRequestUpdate cartRequestUpdate = new CartRequestUpdate();
        cartRequestUpdate.setCartId(cartId);
        cartRequestUpdate.setItemId(itemId);
        cartRequestUpdate.setQuantity(quantity_new);


        mockMvc.perform(put("/api/v1/cart/{cartId}", cartId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequestUpdate)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void remove_ItemFromCartTest_Success() throws Exception {

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
        Long itemId = 1L;

        CartRequestDelete cartRequestDelete = new CartRequestDelete();
        cartRequestDelete.setCartId(cartId);
        cartRequestDelete.setItemId(itemId);

        mockMvc.perform(delete("/api/v1/cart/{cartId}", cartId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequestDelete)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(1L))
                .andExpect(jsonPath("$.user.addresses").isNotEmpty())
                .andExpect(jsonPath("$.cartItems").isArray())
                .andExpect(jsonPath("$.cartItems").isEmpty());
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void remove_ItemFromCartTest_Failure() throws Exception {

        Long userId = 1L;
        Long productId = 1L;
        int quantity = 2;

        CartRequestInsert cartRequestInsert = new CartRequestInsert();
        cartRequestInsert.setUserId(userId);
        cartRequestInsert.setProductId(productId);
        cartRequestInsert.setQuantity(quantity);
        validationService.validateObject(CartRequestInsert.class, cartRequestInsert);
        shoppingCartService.addItemToCart(cartRequestInsert);

        Long cartId = 2L;
        Long itemId = 1L;

        CartRequestDelete cartRequestDelete = new CartRequestDelete();
        cartRequestDelete.setCartId(cartId);
        cartRequestDelete.setItemId(itemId);

        mockMvc.perform(delete("/api/v1/cart/{cartId}", cartId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequestDelete)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void get_CartTest_Success() throws Exception {

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

        mockMvc.perform(get("/api/v1/cart/{cartId}", cartId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(1L))
                .andExpect(jsonPath("$.user.addresses").isNotEmpty())
                .andExpect(jsonPath("$.cartItems").isArray());
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void get_CartTest_Failure() throws Exception {

        Long userId = 1L;
        Long productId = 1L;
        int quantity = 2;

        CartRequestInsert cartRequestInsert = new CartRequestInsert();
        cartRequestInsert.setUserId(userId);
        cartRequestInsert.setProductId(productId);
        cartRequestInsert.setQuantity(quantity);
        validationService.validateObject(CartRequestInsert.class, cartRequestInsert);
        shoppingCartService.addItemToCart(cartRequestInsert);


        Long cartId = 3L;

        mockMvc.perform(get("/api/v1/cart/{cartId}", cartId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    @Transactional
    @Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getCartTotalTest() throws Exception {

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

        mockMvc.perform(get("/api/v1/cart/{cartId}/total", cartId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(699.99 * 2));
    }
}