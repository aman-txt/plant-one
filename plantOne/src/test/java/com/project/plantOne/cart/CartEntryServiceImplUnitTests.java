package com.project.plantOne.cart;

import com.project.plantOne.cartEntry.CartEntry;
import com.project.plantOne.cartEntry.CartEntryRepository;
import com.project.plantOne.cartEntry.CartEntryServiceImpl;
import com.project.plantOne.post.Post;
import com.project.plantOne.post.PostRepository;
import com.project.plantOne.user.User;
import com.project.plantOne.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CartEntryServiceImplUnitTests {

    @Mock
    UserRepository userRepository;

    @Mock
    CartRepository cartRepository;

    @Mock
    PostRepository postRepository;

    @Mock
    CartEntryRepository cartEntryRepository;

    @InjectMocks
    CartEntryServiceImpl cartEntryService;

    CartObjects cartObjects = new CartObjects();
    CartEntry cartEntry = new CartEntry();
    Cart cart = new Cart();

    @BeforeEach
    public void createCartEntryObject(){

        cart = cartObjects.getCartObjects();
        cartEntry = cartObjects.getCartEntryObjects();

    }

    @Test
    public void addToCartEntryTest(){

        Post post = cartObjects.getCartEntryObjects().getPost();
        User user = cart.getUser();
        CartEntry cartEntry = cartObjects.getCartEntryObjects();

        Mockito.when(cartRepository.findByUser(user.getUser_id())).thenReturn(cart);

        Mockito.when(postRepository.findBypostUUID(post.getPostUUID())).thenReturn(post);

        Mockito.when(cartEntryRepository.save(any(CartEntry.class))).thenReturn(cartEntry);

        CartEntry cartEntry1 = cartEntryService.addToCartEntry(post.getPostUUID(), user.getUser_id());

        assertEquals(cartEntry1.getCart().getCartUUID(), cartEntry.getCart().getCartUUID());

    }

    @Test
    public void getCartItemsTest(){

        List<CartEntry> cartEntryList = new ArrayList<>();

        cartEntryList.add(cartEntry);
        Mockito.when(cartEntryRepository.getCartItems(cartEntry.getCart().getUser().getUser_id())).thenReturn(cartEntryList);

        List<CartEntry> cartEntries = cartEntryService.getCartItems(cartEntry.getCart().getUser().getUser_id());
        assertEquals(cartEntryList.get(0).getCartEntryUUID(), cartEntries.get(0).getCartEntryUUID());

    }

    @Test
    public void checkoutCartTest(){

        Post post = cartObjects.getCartEntryObjects().getPost();


        List<CartEntry> cartEntryList = new ArrayList<>();
        cartEntryList.add(cartEntry);

        List<UUID> postUUID = new ArrayList<>();
        postUUID.add(post.getPostUUID());
        Mockito.when(cartRepository.findByUser(any(UUID.class))).thenReturn(cart);
        Mockito.when(cartEntryRepository.findByCart(any(UUID.class))).thenReturn(postUUID);
        Mockito.when(cartEntryRepository.isCheckedOut(any(UUID.class))).thenReturn(null);
        Mockito.when(cartEntryRepository.updateCheckOutStatus(any(UUID.class), any(UUID.class))).thenReturn(1);
        Mockito.when(postRepository.updatePostBuyer(any(UUID.class), any(UUID.class))).thenReturn(1);
        Mockito.when(cartEntryRepository.findByCartUUID(any(UUID.class))).thenReturn(cartEntryList);

        List<CartEntry> cartEntries = cartEntryService.checkoutCartEntry(cartEntry.getCart().getUser().getUser_id());

        assertEquals(cartEntries.get(0).getPost(),cartEntryList.get(0).getPost());

    }

    @Test
    public void deleteCartEntryTest(){

        String result = "Cart entry deleted successfully";
        Mockito.doNothing().when(cartEntryRepository).deleteById(cartEntry.getCartEntryUUID());

        String responseResult = cartEntryService.deleteCartEntry(cartEntry.getCartEntryUUID());

        assertEquals(result, responseResult);

    }






}
