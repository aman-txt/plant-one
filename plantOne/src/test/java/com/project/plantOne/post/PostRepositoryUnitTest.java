package com.project.plantOne.post;


import com.project.plantOne.cart.Cart;
import com.project.plantOne.cart.CartRepository;
import com.project.plantOne.cartEntry.CartEntry;
import com.project.plantOne.cartEntry.CartEntryRepository;
import com.project.plantOne.user.User;
import com.project.plantOne.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryUnitTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartEntryRepository cartEntryRepository;

    @Autowired
    PlantTypeRepository plantTypeRepository;

    @Autowired
    PostTypeRepository postTypeRepository;

    @MockBean
    PostController postController;

    PostObjects postObjects = new PostObjects();
    User user  = new User();
    UUID postUUID;

    @BeforeEach
    public void createPost(){

        Post post = postObjects.getPostObjects();
        Post savedPost = postRepository.save(post);
        postUUID = savedPost.getPostUUID();
    }
    @Test
    public void findByPostTest(){

        Post post = postObjects.getPostObjects();
        Post postResponse = postRepository.findBypostUUID(postUUID);
        assertEquals(postResponse.getPostUUID(),postUUID);
    }

    @Test
    public void updatePostBuyerTest(){

        PostObjects buyer = new PostObjects();
        user = userRepository.save(buyer.getBuyerForUpdate());
        UUID buyerUUID = user.getUser_id();
        int response = postRepository.updatePostBuyer(buyerUUID, postUUID);
        assertEquals(1,response);
    }

    @Test
    public void getActivePostsTest(){

        Cart cart = postObjects.getCartObject();
        User user =  userRepository.findAll().get(1);
        cart.setUser(user);
        cartRepository.save(cart);
        CartEntry cartEntry = postObjects.getCartEntrytObject(cart);
        cartEntryRepository.save(cartEntry);

       List<Post> activePosts = postRepository.getActivePosts(cart.getUser().getUser_id());
       assertTrue(activePosts.size() > 0);

    }


    @Test
    public void insertPlantTypeTest(){

        PlantType plantType = new PlantType();
        UUID uuid = UUID.randomUUID();
        plantType.setPlantTypeId(uuid);
        plantType.setPlantType("Test");
        int response = plantTypeRepository.insertPlantType(uuid.toString(), "Test");
        assertEquals(response, 1);
        plantTypeRepository.delete(plantType);
    }


    @Test
    public void insertPostTypeTest(){

        PostType postType = new PostType();
        UUID uuid = UUID.randomUUID();
        postType.setPostTypeId(uuid);
        postType.setPostType("Test");
        int response = postTypeRepository.insertPostType(uuid.toString(), "Test");
        assertEquals(response, 1);
        postTypeRepository.delete(postType);
    }
    


}
