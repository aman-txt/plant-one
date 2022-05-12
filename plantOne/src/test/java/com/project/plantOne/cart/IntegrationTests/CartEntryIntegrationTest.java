package com.project.plantOne.cart.IntegrationTests;

import com.project.plantOne.AbstractTest;
import com.project.plantOne.PlantOneApplication;
import com.project.plantOne.cart.Cart;
import com.project.plantOne.cart.CartObjects;
import com.project.plantOne.cart.CartRepository;
import com.project.plantOne.cartEntry.CartEntry;
import com.project.plantOne.cartEntry.CartEntryRepository;
import com.project.plantOne.post.Post;
import com.project.plantOne.post.PostObjects;
import com.project.plantOne.post.PostRepository;
import com.project.plantOne.user.User;
import com.project.plantOne.user.UserRepository;
import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.project.plantOne.constants.Constants.CART_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PlantOneApplication.class)
@WebAppConfiguration
@EnableWebMvc
public class CartEntryIntegrationTest extends AbstractTest {


    @Autowired
    CartEntryRepository cartEntryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    PostRepository postRepository;

    int expectedStatus = 200;

    CartObjects cartObjects = new CartObjects();
    User user = new User();
    Cart savedCart = new Cart();
    CartEntry cartEntry= new CartEntry();
    CartEntry savedCartEntry = new CartEntry();
    Post post = new Post();
    Post savedPost = new Post();
    PostObjects postObjects = new PostObjects();

    @Override
    @Before
    public void setUp() {

        user = userRepository.save(cartObjects.getUser());
        post = postObjects.getPostObjects();
        post.setSeller(user);
        savedPost = postRepository.save(post);
        Cart cart = new Cart();
        cart.setUser(user);
        Cart savedCart =  cartRepository.save(cart);

        cartEntry.setCart(savedCart);
        cartEntry.setPost(savedPost);
        savedCartEntry = cartEntryRepository.save(cartEntry);
        super.setUp();

    }

    @Test
    public void addCartEntryTest() throws Exception {

        String uri = CART_URL + "/add/{user_id}/{post_id}";

        Optional<CartEntry> cartEntry1 = cartEntryRepository.findById(savedCartEntry.getCartEntryUUID());
        UUID userUUID = userRepository.findAll().get(0).getUser_id();
        UUID postUUID = postRepository.findAll().get(0).getPostUUID();


        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.post(uri,userUUID,postUUID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, status);


    }

    @Test
    public void checkoutCartEntryTest() throws Exception {

        String uri = CART_URL + "/checkout/{user_id}";

        List<CartEntry> cartEntryList = new ArrayList<>();


        Optional<CartEntry> cartEntry1 = cartEntryRepository.findById(savedCartEntry.getCartEntryUUID());
        UUID userUUID = cartRepository.findAll().get(0).getUser().getUser_id();
        cartEntryList.add(cartEntry1.get());

        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.post(uri,userUUID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, status);


    }

    @Test
    public void getCartItemsTest() throws Exception {

        String uri = CART_URL + "/getCartItems/{user_id}";


        Optional<CartEntry> cartEntry1 = cartEntryRepository.findById(savedCartEntry.getCartEntryUUID());
        UUID userUUID = cartRepository.findAll().get(0).getUser().getUser_id();


        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(uri,userUUID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, status);



    }

    @Test
    public void deleteCartEntryTest() throws Exception {

        String uri = CART_URL + "/deleteCartItem/{cart_entry_id}";

        Optional<CartEntry> cartEntry1 = cartEntryRepository.findById(savedCartEntry.getCartEntryUUID());
        UUID cartEntryUUID = cartEntryRepository.findAll().get(0).getCartEntryUUID();


        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.delete(uri,cartEntryUUID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, status);

    }

//    @After
//    public void deleteCartEntry(){
//
//        cartEntryRepository.deleteById(savedCartEntry.getCartEntryUUID());
//        cartRepository.deleteById(savedCart.getCartUUID());
//        userRepository.deleteById(user.getUser_id());
//    }

}
