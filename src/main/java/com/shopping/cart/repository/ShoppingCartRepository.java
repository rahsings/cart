package com.shopping.cart.repository;

import com.shopping.cart.model.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {

    @Transactional
    @Query(value = "SELECT sc.* FROM shopping_carts sc WHERE sc.user_id = :userId", nativeQuery = true)
    Optional<ShoppingCart> findByUser(@Param("userId") Long userId);
}

