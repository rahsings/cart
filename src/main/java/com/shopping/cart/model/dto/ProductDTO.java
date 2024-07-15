package com.shopping.cart.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 9223372036854775807L;
    private Long id;
    private String name;
    private BigDecimal price;
    private String prodInfo;
    private String prodType;
    private String brand;
}
