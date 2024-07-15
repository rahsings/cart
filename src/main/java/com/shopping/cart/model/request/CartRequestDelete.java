package com.shopping.cart.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class CartRequestDelete implements Serializable {

    private static final long serialVersionUID = 9223372036854775807L;
    private Long itemId;
    private Long cartId;
}
