package com.shopping.cart.model.response;

import com.shopping.cart.model.dto.CartItemDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class CartResponse {

    private Long cartId;
    private Set<CartItemDTO> cartItemSet;
    private BigDecimal total;
}
