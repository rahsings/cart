package com.shopping.cart.mapper;

import com.shopping.cart.model.dto.UserDTO;
import com.shopping.cart.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    List<UserDTO> userToUserDTO(List<User> user);

    @Mapping(target = "addresses", ignore = true)
    User userDTOToUser(UserDTO user);

    UserDTO userToUserDTO(User user);
}
