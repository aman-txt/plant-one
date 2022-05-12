package com.project.plantOne.cartEntry;

import java.util.List;
import java.util.UUID;

public interface CartEntryService {

    public CartEntry addToCartEntry(UUID postUUID, UUID userUUID);

    public List<CartEntry> getCartItems(UUID userUUID);

    public String deleteCartEntry(UUID cartUUID);

    public List<CartEntry> checkoutCartEntry(UUID userUUID);
}
