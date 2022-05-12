package com.project.plantOne.cart;

import com.project.plantOne.cartEntry.CartEntry;
import com.project.plantOne.cartEntry.CartEntryRepository;
import com.project.plantOne.post.PostController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class CartRepositoryUnitTests {

    @Autowired
    CartEntryRepository cartEntryRepository;

    @Autowired
    CartRepository cartRepository;

    @MockBean
    PostController postController;

    CartObjects cartObjects = new CartObjects();
    CartEntry cartEntry = new CartEntry();
    Cart cart = new Cart();
    Cart cart1 = new Cart();

    @BeforeEach
    public void createCartEntryObject(){

        cart = cartObjects.getCartObjects();
        cartEntry = cartObjects.getCartEntryObjects();
        cart1 = cartRepository.save(cart);


    }

    @Test
    public void findByCartTest(){

        cartEntryRepository.save(cartEntry);
        CartEntry cartEntries = cartEntryRepository.findAll().get(0);
        List<UUID> postUUID = cartEntryRepository.findByCart(cartEntries.getCart().getCartUUID());

        assertThat(postUUID.size() >0);

    }

    @Test
    public void isCheckedOutTest(){

        cartEntry.setCheckedOut(true);
        CartEntry savedCartEntry = cartEntryRepository.save(cartEntry);
        Optional<CartEntry> cartEntry= cartEntryRepository.findById(savedCartEntry.getCartEntryUUID());
        UUID postUUID = cartEntry.get().getPost().getPostUUID();
        UUID actualPostUUID = cartEntryRepository.isCheckedOut(postUUID);

        assertThat(actualPostUUID).isInstanceOfAny(UUID.class);
    }

    @Test
    public void updateCheckOutStatusTest(){

        cartEntry.setCheckedOut(true);
        cartEntryRepository.save(cartEntry);
        CartEntry cartEntry= cartEntryRepository.findAll().get(0);
        UUID postUUID = cartEntry.getPost().getPostUUID();
        UUID cartUUID = cartEntry.getCart().getCartUUID();
        int response = cartEntryRepository.updateCheckOutStatus(cartUUID,postUUID);

        assertEquals(response, 1);

    }

    @Test
    public void softDeleteItemsTest() {

        cartEntry.setSoftDelete(true);
        cartEntryRepository.save(cartEntry);
        CartEntry cartEntry = cartEntryRepository.findAll().get(0);
        UUID postUUID = cartEntry.getPost().getPostUUID();
        UUID cartUUID = cartEntry.getCart().getCartUUID();
        int response = cartEntryRepository.updateCheckOutStatus(cartUUID, postUUID);

        assertEquals(response, 1);

    }

    @Test
    public void findByCartUUIDTest(){

        cartEntry.setCheckedOut(true);
        cartEntry.setSoftDelete(false);
        cartEntryRepository.save(cartEntry);
        List<CartEntry> cartEntry = cartEntryRepository.findAll();
        UUID cartUUID = cartEntry.get(0).getCart().getCartUUID();
        List<CartEntry> responseCartEntries = cartEntryRepository.findByCartUUID(cartUUID);

        assertThat(responseCartEntries.size() >0);

    }

    @Test
    public void getCartItems(){

        cartEntry.setCheckedOut(false);
        cartEntry.setSoftDelete(false);
        cartEntryRepository.save(cartEntry);
        CartEntry cartEntry = cartEntryRepository.findAll().get(0);
        UUID userUUID = cartEntry.getCart().getUser().getUser_id();

        List<CartEntry> cartEntries = cartEntryRepository.getCartItems(userUUID);

        assertThat(cartEntries.size() > 1);

    }

    @Test
    public void findByUserTest(){

        UUID userUUID = cartRepository.findAll().get(0).getUser().getUser_id();
        Cart responseCart = cartRepository.findByUser(userUUID);

        assertThat(responseCart.getUser().getFirst_name()).isInstanceOfAny(String.class);

    }

}
