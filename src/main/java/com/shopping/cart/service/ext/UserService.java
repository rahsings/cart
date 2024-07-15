package com.shopping.cart.service.ext;

import com.shopping.cart.model.dto.UserDTO;
import com.shopping.cart.model.entity.User;

import java.util.List;

public interface UserService {

    User get(Long id);

    List<UserDTO> getAll();

    UserDTO getUserDTO(User user);
}
