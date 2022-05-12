package com.project.plantOne.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    @Query("from Cart c where c.user.user_id = :id")
    Cart findByUser(@Param("id") UUID userUUID);




}
