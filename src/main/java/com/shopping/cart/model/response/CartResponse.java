package com.shopping.cart.model.response;

import com.shopping.cart.model.dto.CartItemDTO;
import com.shopping.cart.model.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CartResponse implements Serializable {

    private static final long serialVersionUID = 9223372036854775807L;

    private Long id;
    private UserDTO user;
    private Set<CartItemDTO> cartItems = new HashSet<>();
}
