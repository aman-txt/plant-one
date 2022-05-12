package com.project.plantOne.cart;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.project.plantOne.cartEntry.CartEntry;
import com.project.plantOne.cartEntry.CartEntryController;
import com.project.plantOne.cartEntry.CartEntryRepository;
import com.project.plantOne.cartEntry.CartEntryServiceImpl;
import com.project.plantOne.post.PostRepository;
import com.project.plantOne.user.UserRepository;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.project.plantOne.constants.Constants.CART_URL;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CartEntryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ComponentScan(basePackages = "com.project.plantOne.cartEntry")
public class CartEntryControllerUnitTests {

    @Configuration
    @EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
    static class ContextConfiguration { }

    @MockBean
    CartController cartController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    CartEntryServiceImpl cartEntryService;

    @MockBean
    CartEntryRepository cartEntryRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    PostRepository postRepository;

    @MockBean
    CartRepository cartRepository;

    @Test
    public void addCartEntryTest() throws Exception {

        String uri = CART_URL + "/add/{user_id}/{post_id}";
        CartObjects cartObjects = new CartObjects();
        CartEntry cartEntry = cartObjects.getCartEntryObjects();

        Mockito.when(cartController.checkIfUserExists(Mockito.any(UUID.class))).thenReturn(false);
        Mockito.doNothing().when(cartController).addToCart(Mockito.any(UUID.class));
        Mockito.when(cartEntryService.addToCartEntry(cartEntry.getCart().getUser().getUser_id(),cartEntry.getPost().getPostUUID())).thenReturn(cartEntry);

        mockMvc.perform(post(uri,cartEntry.getCart().getUser().getUser_id(),cartEntry.getPost().getPostUUID()).content(objectMapper.writeValueAsString(cartEntry))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getCartItemsTest() throws Exception{

        String uri = CART_URL + "/getCartItems/{user_id}";
        List<CartEntry> cartEntryList = new ArrayList<>();

        CartObjects cartObjects = new CartObjects();

        CartEntry cartEntry = cartObjects.getCartEntryObjects();
        cartEntryList.add(cartEntry);

        Mockito.when(cartEntryService.getCartItems(Mockito.any(UUID.class))).thenReturn(cartEntryList);

        mockMvc.perform(get(uri,cartEntry.getCart().getUser().getUser_id()).content(objectMapper.writeValueAsString(cartEntryList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void checkoutCartEntryTest() throws Exception{

        String uri = CART_URL + "/checkout/{user_id}";

        CartObjects cartObjects = new CartObjects();
        List<CartEntry> cartEntryList = new ArrayList<>();

        CartEntry cartEntry = cartObjects.getCartEntryObjects();
        cartEntryList.add(cartEntry);

        Mockito.when(cartEntryService.checkoutCartEntry(cartEntry.getCart().getUser().getUser_id())).thenReturn(cartEntryList);

        mockMvc.perform(post(uri,cartEntry.getCart().getUser().getUser_id()).content(objectMapper.writeValueAsString(cartEntryList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void deleteCartEntryTests() throws Exception {

        String uri = CART_URL + "/deleteCartItem/{cart_entry_id}";

        CartObjects cartObjects = new CartObjects();
        Cart cart = cartObjects.getCartObjects();

        Mockito.when(cartEntryService.deleteCartEntry(Mockito.any(UUID.class))).thenReturn("Cart entry deleted successfully");

        mockMvc.perform(delete(uri,cart.getCartUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }


}
