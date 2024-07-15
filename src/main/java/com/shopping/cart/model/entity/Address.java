package com.shopping.cart.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "addresses")
public class Address implements Serializable {

    private static final long serialVersionUID = 9223372036854775807L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "street")
    private String street;

    @Column(name = "locality", nullable = false)
    private String locality;

    @Column(name = "landmark", nullable = false)
    private String landmark;

    @Column(name = "house_no", unique = true, nullable = false)
    private String houseNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}

