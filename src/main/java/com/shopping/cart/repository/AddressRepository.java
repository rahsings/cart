package com.shopping.cart.repository;

import com.shopping.cart.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    @Transactional
    @Query(value = "select * from addresses where user_id =:id",nativeQuery = true)
    Set<Address> getAddress(Long id);
}
