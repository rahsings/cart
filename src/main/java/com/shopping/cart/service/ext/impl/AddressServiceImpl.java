package com.shopping.cart.service.ext.impl;

import com.shopping.cart.mapper.AddressMapper;
import com.shopping.cart.model.dto.AddressDTO;
import com.shopping.cart.model.entity.Address;
import com.shopping.cart.repository.AddressRepository;
import com.shopping.cart.service.ext.AddressService;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "addressById", key = "#addressId")
    private Set<Address> getAddressByUserId(Long userId){
        return addressRepository.getAddress(userId);
    }

    public Set<AddressDTO> getAddress(Long id){
        return addressMapper.addressToAddressDTO(getAddressByUserId(id));
    }
}
