package com.shopping.cart.controller;

import com.shopping.cart.exception.CartNotFoundException;
import com.shopping.cart.model.dto.ShoppingCartDTO;
import com.shopping.cart.model.request.CartRequestDelete;
import com.shopping.cart.model.request.CartRequestInsert;
import com.shopping.cart.model.request.CartRequestUpdate;
import com.shopping.cart.model.response.CartResponse;
import com.shopping.cart.service.ShoppingCartService;
import com.shopping.cart.service.ValidationService;
import com.shopping.cart.util.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final ValidationService validationService;

    @Operation(summary = "Adds item to cart, if empty creates new", description = "Required: productId, userId, quantity")
    @PostMapping()
    public ResponseEntity<ShoppingCartDTO> addItemToCart(@RequestBody CartRequestInsert cartRequestInsert) {
        validationService.validateObject(CartRequestInsert.class, cartRequestInsert);
        ShoppingCartDTO cart = shoppingCartService.addItemToCart(cartRequestInsert);
        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "Updates item in cart, remove item by quantity if negative", description = "Required: cartId, itemId, quantity")
    @PutMapping("/{cartId}")
    public ResponseEntity<ShoppingCartDTO> updateItemQuantity(@PathVariable Long cartId, @RequestBody CartRequestUpdate cartRequestUpdate) {
        cartRequestUpdate.setCartId(cartId);
        validationService.validateObject(CartRequestUpdate.class, cartRequestUpdate);
        ShoppingCartDTO cart = shoppingCartService.updateItemQuantity(cartRequestUpdate);
        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "Evicts item from cart", description = "Required: cartId, itemId")
    @DeleteMapping("/{cartId}")
    public ResponseEntity<ShoppingCartDTO> removeItemFromCart(@PathVariable Long cartId, @RequestBody CartRequestDelete cartRequestDelete) {
        cartRequestDelete.setCartId(cartId);
        validationService.validateObject(CartRequestDelete.class, cartRequestDelete);
        ShoppingCartDTO cart = shoppingCartService.removeItemFromCart(cartRequestDelete);
        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "Gives Cart Details", description = "Required: cartId")
    @GetMapping("/{cartId}")
    public ResponseEntity<ShoppingCartDTO> getCart(@PathVariable Long cartId) throws CartNotFoundException {
        ShoppingCartDTO cart = shoppingCartService.getCart(cartId);
        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "Gives cart total cost value and Cart Details", description = "Required: cartId")
    @GetMapping("/{cartId}/total")
    public ResponseEntity<CartResponse> getCartTotal(@PathVariable Long cartId) {
        CartResponse total = shoppingCartService.getCartTotal(cartId);
        return ResponseEntity.ok(total);
    }
}
