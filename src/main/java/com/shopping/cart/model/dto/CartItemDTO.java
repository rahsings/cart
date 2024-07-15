package com.shopping.cart.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CartItemDTO implements Serializable {

    private static final long serialVersionUID = 9223372036854775807L;

    private Long id;
    private ProductDTO product;
    private int quantity;
}
