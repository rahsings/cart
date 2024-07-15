package com.shopping.cart.service.ext;

import com.shopping.cart.model.dto.AddressDTO;

import java.util.Set;

public interface AddressService {
    Set<AddressDTO> getAddress(Long id);
}
