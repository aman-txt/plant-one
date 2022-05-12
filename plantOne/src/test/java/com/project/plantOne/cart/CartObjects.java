package com.project.plantOne.cart;


import com.project.plantOne.post.PostObjects;
import com.project.plantOne.cartEntry.CartEntry;
import com.project.plantOne.user.User;

import java.util.Random;
import java.util.UUID;

public class CartObjects {

    static int randomStringLength = 4;

    public Cart getCartObjects(){

        Cart cart = new Cart();
        cart.setCartUUID(UUID.randomUUID());
        cart.setUser(getUser());

        return cart;
    }


    public CartEntry getCartEntryObjects(){

        CartEntry cartEntry = new CartEntry();
        PostObjects postObjects = new PostObjects();

        cartEntry.setCartEntryUUID(UUID.randomUUID());
        cartEntry.setCheckedOut(false);
        cartEntry.setSoftDelete(false);
        cartEntry.setPost(postObjects.getPostObjects());
        cartEntry.setCart(getCartObjects());

        return cartEntry;
    }

    public User getUser(){

        User user = new User();
        user.setUser_id(UUID.randomUUID());
        user.setActiveUser(true);
        user.setFirst_name("Test");
        user.setLast_name("Test");
        user.setCity("Test City");
        byte[] ar = new byte[randomStringLength];
        new Random().nextBytes(ar);
        String testEmail = "test" + getRandomString() + "@gmail.com";
        user.setEmail(testEmail);
        user.setUser_role("TestRole");

        return user;

    }

    public String getRandomString(){

        // create a string of all characters
        String string = "12345676890abcdefg";

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = randomStringLength;

        for(int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(string.length());

            // get character specified by index
            // from the string
            char randomChar = string.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static int getRandomStringLength() {
        return randomStringLength;
    }

    public static void setRandomStringLength(int randomStringLength) {
        CartObjects.randomStringLength = randomStringLength;
    }
}
