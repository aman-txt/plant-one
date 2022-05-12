package com.project.plantOne.cartEntry;

import com.project.plantOne.cart.Cart;
import com.project.plantOne.cart.CartRepository;
import com.project.plantOne.post.PostRepository;
import com.project.plantOne.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartEntryServiceImpl implements CartEntryService{

    @Autowired
    CartEntryRepository cartEntryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CartRepository cartRepository;



    public CartEntry addToCartEntry(UUID postUUID, UUID userUUID)
    {

        CartEntry cartEntry = new CartEntry();
        cartEntry.setCart(cartRepository.findByUser(userUUID));
        cartEntry.setPost(postRepository.findBypostUUID(postUUID));
        //cartEntry.setUser(userRepository.getById(userUUID));
        cartEntry.setCheckedOut(false);
        cartEntry.setSoftDelete(false);
        return cartEntryRepository.save(cartEntry);
    }

    public List<CartEntry> getCartItems(UUID userUUID){

        return cartEntryRepository.getCartItems(userUUID);

    }

    public String deleteCartEntry(UUID cartUUID){

        String result;
        try{
            cartEntryRepository.deleteById(cartUUID);
            result = "Cart entry deleted successfully";

        }catch(Exception exception){
            result = "Exception:"+exception;
        }
        return result;
    }



    public List<CartEntry> checkoutCartEntry(UUID userUUID) {

        Cart cart = cartRepository.findByUser(userUUID);
        UUID cartUUID = cart.getCartUUID();
        List<UUID> postUUID = cartEntryRepository.findByCart(cartUUID);


        int numberOfPosts = postUUID.size();
        int itemsAvailable = 0;
        for (UUID singlePost : postUUID) {
            UUID expr = cartEntryRepository.isCheckedOut(singlePost);
            if (expr == null) {
                itemsAvailable++;
            }
        }

            if (itemsAvailable == numberOfPosts) //All the items in the cart are available for Checking out
            {
                for (UUID checkOutPost : postUUID) { // Now check out

                    cartEntryRepository.updateCheckOutStatus(cartUUID, checkOutPost);
                    postRepository.updatePostBuyer(userUUID, checkOutPost); //The post table gets updated with the buyer

                }
            } else if (itemsAvailable != numberOfPosts) {

                for (UUID checkOutPost : postUUID) {

                    cartEntryRepository.softDeleteItems(checkOutPost, cartUUID);
                }
            }

        return cartEntryRepository.findByCartUUID(cartUUID);
    }



}
