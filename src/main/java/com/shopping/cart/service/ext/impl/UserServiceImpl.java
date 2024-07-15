package com.shopping.cart.service.ext.impl;

import com.shopping.cart.exception.UserNotFoundException;
import com.shopping.cart.mapper.UserMapper;
import com.shopping.cart.model.dto.UserDTO;
import com.shopping.cart.model.entity.User;
import com.shopping.cart.model.enums.ErrorCode;
import com.shopping.cart.repository.UserRepository;
import com.shopping.cart.service.ext.UserService;
import com.shopping.cart.util.Utils;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "userById", key = "#id")
    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(HttpStatus.BAD_REQUEST, Utils.get(id, ErrorCode.USER.getMessage()),
                        ErrorCode.USER.getKey()));
    }

    @Cacheable(value = "users")
    public List<UserDTO> getAll() {
        return userMapper.userToUserDTO(userRepository.findAll());
    }

    @Override
    public UserDTO getUserDTO(User user) {
        return userMapper.userToUserDTO(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void evictCacheUsers() {
    }

    @Transactional
    @CacheEvict(value = "userById", key = "#id")
    public void evictCacheForID(Long id) {
    }
}
