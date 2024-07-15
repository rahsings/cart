package com.shopping.cart.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CartRequestInsert implements Serializable {

    private static final long serialVersionUID = 9223372036854775807L;
    private Long productId;
    private Long userId;
    private Integer quantity;
}

