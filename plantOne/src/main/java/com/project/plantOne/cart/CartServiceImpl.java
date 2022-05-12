package com.project.plantOne.cart;

import com.project.plantOne.user.User;
import com.project.plantOne.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    public void addToCart(UUID userUUID){

        Cart cart = new Cart();
        User cartUser = new User();
        Optional<User> user = userRepository.findById(userUUID);
        if(user!= null) {
             cartUser = user.get();
        }
        cart.setUser(cartUser);
        cartRepository.save(cart);

    }

    public boolean checkIfUserExists(UUID userUUID) {

        if(cartRepository.findByUser(userUUID)!= null)
            return true;
        else
            return false;
    }
}

