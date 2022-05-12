package com.project.plantOne.cart;

import java.util.UUID;

public interface CartService {

    public void addToCart(UUID userUUID);

    public boolean checkIfUserExists(UUID userUUID);


}
