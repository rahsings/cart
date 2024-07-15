package com.shopping.cart.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ShoppingCartDTO implements Serializable {

    private Long id;
    private UserDTO user;
    private Set<CartItemDTO> cartItems = new HashSet<>();
}
