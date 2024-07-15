package com.shopping.cart.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class AddressDTO implements Serializable {

    private static final long serialVersionUID = 9223372036854775807L;
    private Long id;
    private String pinCode;
    private String city;
    private String state;
    private String country;
    private String street;
    private String locality;
    private String landmark;
    private String houseNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDTO that = (AddressDTO) o;
        return Objects.equals(pinCode, that.pinCode) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(country, that.country) && Objects.equals(street, that.street)
                && Objects.equals(locality, that.locality) && Objects.equals(landmark, that.landmark)
                && Objects.equals(houseNo, that.houseNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pinCode, city, state, country, street, locality, landmark, houseNo);
    }
}
