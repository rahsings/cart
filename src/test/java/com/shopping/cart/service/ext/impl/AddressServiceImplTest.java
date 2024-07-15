package com.shopping.cart.service.ext.impl;

import com.shopping.cart.mapper.AddressMapper;
import com.shopping.cart.model.dto.AddressDTO;
import com.shopping.cart.model.entity.Address;
import com.shopping.cart.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        address = createAddress();
        addressDTO = createAddressDTO();
    }

    @Test
    void test_GetAddressByUserId() {
        Long userId = 1L;
        Set<Address> addresses = Set.of(address);

        when(addressRepository.getAddress(userId)).thenReturn(addresses);
        when(addressMapper.addressToAddressDTO(addresses)).thenReturn(Set.of(addressDTO));

        Set<AddressDTO> result = addressService.getAddress(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(addressDTO.getId(), result.iterator().next().getId());
        verify(addressRepository).getAddress(userId);
    }

    @Test
    void test_GetAddressEmpty() {
        Long userId = 1L;

        when(addressRepository.getAddress(userId)).thenReturn(Collections.emptySet());
        when(addressMapper.addressToAddressDTO(Collections.emptySet())).thenReturn(Collections.emptySet());

        Set<AddressDTO> result = addressService.getAddress(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(addressRepository).getAddress(userId);
    }

    private AddressDTO createAddressDTO() {
        AddressDTO p1 = new AddressDTO();
        p1.setId(1L);
        p1.setCity("NYC");
        p1.setState("NY");
        p1.setPinCode("12345");
        p1.setLandmark("Near Liberty");
        p1.setLocality("123 Main St");
        p1.setHouseNo("#12");
        return p1;

    }

    private Address createAddress(){
        Address p1 = new Address();
        p1.setId(1L);
        p1.setCity("NYC");
        p1.setState("NY");
        p1.setPinCode("12345");
        p1.setLandmark("Near Liberty");
        p1.setLocality("123 Main St");
        p1.setHouseNo("#12");
        return p1;
    }
}

