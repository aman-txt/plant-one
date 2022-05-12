package com.project.plantOne.cartEntry;

import com.project.plantOne.cart.CartController;
import com.project.plantOne.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.project.plantOne.constants.Constants.CART_URL;

@RestController
@RequestMapping(path = CART_URL)
public class CartEntryController {

    @Autowired
    private CartEntryServiceImpl cartEntryService;

    @Autowired
    private CartController cartController;

    @Autowired
    PostRepository postRepository;


    @PostMapping(value = "/add/{user_id}/{post_id}")
    public CartEntry addCartEntry(@PathVariable ("user_id")UUID userUUID, @PathVariable ("post_id")UUID postUUID) {

        CartEntry cartEntry = new CartEntry();
        boolean userExists = cartController.checkIfUserExists(userUUID);
        if(!userExists) {
            cartController.addToCart(userUUID);
        }
        return cartEntryService.addToCartEntry(postUUID, userUUID);

    }

    @GetMapping(value = "/getCartItems/{user_id}")
    public List<CartEntry> getCartItems(@PathVariable ("user_id")UUID userUUID) {

        return cartEntryService.getCartItems(userUUID);
    }


    @PostMapping(value = "/checkout/{user_id}")
    public List<CartEntry> checkoutCartEntry(@PathVariable ("user_id")UUID userUUID)
    {
        return cartEntryService.checkoutCartEntry(userUUID);
    }

    @DeleteMapping(value = "/deleteCartItem/{cart_entry_id}")
    public String deleteCartEntry(@PathVariable("cart_entry_id") UUID cartEntryUUID){

        return cartEntryService.deleteCartEntry(cartEntryUUID);
    }

}
