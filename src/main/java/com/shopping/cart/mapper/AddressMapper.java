package com.shopping.cart.mapper;

import com.shopping.cart.model.dto.AddressDTO;
import com.shopping.cart.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);

    Set<AddressDTO> addressToAddressDTO(Set<Address> address);

    @Mapping(target = "user", ignore = true)
    Set<Address> addressDTOToAddress(Set<AddressDTO> address);
}
