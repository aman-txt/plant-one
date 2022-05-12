package com.project.plantOne.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class CartController {

    @Autowired
    CartService cartService;

    public void addToCart(UUID userUUID){

        cartService.addToCart(userUUID);

    }

    public boolean checkIfUserExists(UUID userUUID) {

        return cartService.checkIfUserExists(userUUID);
    }

}
