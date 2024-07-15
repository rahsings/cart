package com.shopping.cart.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 9223372036854775807L;

    private Long id;
    private String mobile;
    private String email;
    private String name;
    private Set<AddressDTO> addresses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(mobile, userDTO.mobile) && Objects.equals(email, userDTO.email) && Objects.equals(name, userDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mobile, email, name);
    }
}
