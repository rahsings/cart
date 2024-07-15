package com.shopping.cart.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "shopping_carts")
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 9223372036854775807L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CartItem> cartItems = new HashSet<>();

    @Transactional
    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setShoppingCart(this);
    }

    @Transactional
    public CartItem addCartItemByQuantity(CartItem cartItem, int quantity) {
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItems.add(cartItem);
        cartItem.setShoppingCart(this);
        return cartItem;
    }

    @Transactional
    public CartItem removeCartItemByQuantity(CartItem cartItem, int quantity) {
        cartItem.setQuantity(cartItem.getQuantity() - quantity);
        cartItems.add(cartItem);
        cartItem.setShoppingCart(this);
        return cartItem;
    }

    @Transactional
    public void removeCartItem(CartItem cartItem) {
        cartItems = cartItems.stream().filter(cartItem1 -> !Objects.equals(cartItem.getId(),cartItem1.getId())).collect(Collectors.toSet());
        cartItem.setShoppingCart(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

