package com.shopping.cart.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CartRequestUpdate extends CartRequestDelete implements Serializable {
    private static final long serialVersionUID = 9223372036854775807L;
    private Integer quantity;
}
