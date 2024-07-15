package com.shopping.cart.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class CartResponseTotal extends CartResponse implements Serializable {

    private static final long serialVersionUID = 9223372036854775807L;
    private BigDecimal total;
}
