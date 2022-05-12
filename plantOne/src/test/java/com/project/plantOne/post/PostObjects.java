package com.project.plantOne.post;

import com.project.plantOne.cart.Cart;
import com.project.plantOne.cartEntry.CartEntry;
import com.project.plantOne.user.User;
import com.project.plantOne.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


import static com.project.plantOne.constants.Constants.PLANT_TYPES;
import static com.project.plantOne.constants.Constants.POST_TYPES;

public class PostObjects {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    static int price = 200;
    static int randomStringLength = 4;

    public User getBuyerUserObjects(){

        User user = new User();
        user.setUser_id(UUID.randomUUID());
        user.setActiveUser(true);
        user.setFirst_name("Test");
        user.setLast_name("Test");
        user.setCity("Test City");
        byte[] ar = new byte[randomStringLength];
        new Random().nextBytes(ar);
        String testEmail = "test" + getRandomString() + "@yahoo.com";
        user.setEmail(testEmail);
        user.setUser_role("TestRole");

        return user;

    }

    public User getSellerUserObjects(){

        User user = new User();
        user.setUser_id(UUID.randomUUID());
        user.setActiveUser(true);
        user.setFirst_name("Test2");
        user.setLast_name("Test2");
        user.setCity("Test City2");

        Random rnd = new Random();
        String testEmail = "test" + getRandomString() + "@gmail.com";
        user.setEmail(testEmail);
        user.setUser_role("TestRole2");

        return user;

    }


    public Post getPostObjects(){

        Post post = new Post();

        post.setPostUUID(UUID.randomUUID());
        post.setTitle("Test");
        post.setPrice(price);
        post.setTimestamp(new Date());
        post.setIs_archive(false);
        post.setSeller(getSellerUserObjects());
        post.setBuyer(getBuyerUserObjects());


        return post;
    }

    public List<PlantType> getPlantType(){

        List<PlantType> plantTypeList = new ArrayList<>();
        PlantType plantType = new PlantType();
        for (int i = 0; i < PLANT_TYPES.size(); i++) {
            plantType.setPlantTypeId(UUID.randomUUID());
            plantType.setPlantType(PLANT_TYPES.get(i));
            plantTypeList.add(plantType);
        }
        return plantTypeList;

    }

    public List<PostType> getPostType(){

        List<PostType> postTypeList = new ArrayList<>();
        PostType postType = new PostType();
        for (int i = 0; i < POST_TYPES.size(); i++) {
            postType.setPostTypeId(UUID.randomUUID());
            postType.setPostType(POST_TYPES.get(i));
            postTypeList.add(postType);
        }
        return postTypeList;

    }

    public User getBuyerForUpdate(){

        User user = new User();
        user.setUser_id(UUID.randomUUID());
        user.setActiveUser(true);
        user.setFirst_name("Test2");
        user.setLast_name("Test2");
        user.setCity("Test City2");
        String testEmail = "test" + getRandomString() + "@gmail.com";
        user.setEmail(testEmail);
        user.setUser_role("TestRole2");

        return user;

    }

    public Cart getCartObject(){

        Cart cart = new Cart();

        return cart;
    }


    public CartEntry getCartEntrytObject(Cart cart){

        CartEntry cartEntry = new CartEntry();
        cartEntry.setCartEntryUUID(UUID.randomUUID());
        cartEntry.setCart(cart);
        cartEntry.setPost(getPostObjects());
        cartEntry.setSoftDelete(false);
        cartEntry.setCheckedOut(false);

        return cartEntry;
    }

    public PostResponse getPostResponseObject(){

        PostResponse postResponse = new PostResponse();

        postResponse.setPostUUID(UUID.randomUUID());
        postResponse.setTitle("Test");
        postResponse.setPrice(price);
        postResponse.setTimestamp(new Date());
        postResponse.setIs_archive(false);
        postResponse.setBuyer(getBuyerUserObjects());
        postResponse.setSeller(getSellerUserObjects());
        postResponse.setLikedByUser(false);

        return postResponse;

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

    public static int getPrice() {
        return price;
    }

    public static void setPrice(int price) {
        PostObjects.price = price;
    }

    public static int getRandomStringLength() {
        return randomStringLength;
    }

    public static void setRandomStringLength(int randomStringLength) {
        PostObjects.randomStringLength = randomStringLength;
    }
}
