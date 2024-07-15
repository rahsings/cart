package com.shopping.cart.service;

import com.shopping.cart.model.entity.CartItem;
import com.shopping.cart.model.entity.Product;
import com.shopping.cart.model.entity.ShoppingCart;
import com.shopping.cart.model.entity.User;
import com.shopping.cart.model.request.CartRequestInsert;
import com.shopping.cart.repository.ShoppingCartRepository;
import com.shopping.cart.service.ext.ProductService;
import com.shopping.cart.service.ext.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.shopping.cart.util.Utils.createCartItemNotFoundException;
import static com.shopping.cart.util.Utils.createCartNotFoundException;

@Service
@RequiredArgsConstructor
public class ShoppingCartRepositoryService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final ProductService productService;

    @Transactional(readOnly = true)
    @Cacheable(value = "shoppingCartByUserId", key = "#userId")
    public ShoppingCart getShoppingCart(Long userId) {
        User userOptional = userService.get(userId);
        return shoppingCartRepository.findByUser(userId)
                .orElseGet(() -> createNewShoppingCart(userOptional.getId()));
    }

    private ShoppingCart createNewShoppingCart(Long userId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(userService.get(userId));
        return shoppingCart;
    }

    public CartItem findCartItem(ShoppingCart cart, Long itemId) {
        return cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getId().equals(itemId))
                .findFirst().orElseThrow(()->createCartItemNotFoundException(itemId));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "shoppingCartById", key = "#cartId")
    public ShoppingCart get(Long cartId) {
        return shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> createCartNotFoundException(cartId));
    }

    public void deleteCartIfEmpty(ShoppingCart cart) {
        if (cart.getCartItems().isEmpty()) {
            shoppingCartRepository.delete(cart);
        }
    }

    public void save(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
    }

    public Product getProduct(CartRequestInsert item) {
        return productService.get(item.getProductId());
    }

    @Transactional
    @CacheEvict(value = "shoppingCartById", key = "#cartId")
    public void evictCartItemsCacheForCartId(Long cartId) {
    }

    @Transactional
    @CacheEvict(value = "shoppingCartByUserId", key = "#userId")
    public void evictCartItemsCacheForUserId(Long userId) {
    }
}

