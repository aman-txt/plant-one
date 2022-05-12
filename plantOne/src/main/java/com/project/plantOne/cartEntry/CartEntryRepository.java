package com.project.plantOne.cartEntry;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.project.plantOne.constants.Constants.*;

@Repository
public interface CartEntryRepository extends JpaRepository<CartEntry, UUID> {

    @Query(value = SEARCH_CART_BY_POST)
    List<UUID> findByCart(@Param("id") UUID cartUUID);

    @Query(value = POST_CHECKOUT_STATUS)
    UUID isCheckedOut(@Param("postId") UUID postUUID);

    //Update isCheckedOut flag to 1. All the posts will be checked out
    @Modifying
    @Transactional
    @Query(value = UPDATE_POST_CHECKOUT_STATUS)
    int updateCheckOutStatus(@Param("cartId") UUID cartUUID, @Param("postId") UUID postUUID);

    @Modifying
    @Transactional
    @Query(value = SOFT_DELETE_POST)
    int softDeleteItems(@Param("postId") UUID postUUID, @Param("cartId") UUID cartUUID);

    @Query(value =FIND_BY_CART_ID)
    List<CartEntry> findByCartUUID(@Param("id") UUID cartUUID);

    @Query(value = GET_CART_ITEMS)
    List<CartEntry> getCartItems(@Param("userID") UUID userUUID);


    /* checkedOut  softDelete orderStatus
        false       false      New order
        true        false      Order can/has been checked out
        false       true       Order has been checked out by other customer

     */

}

