package com.project.plantOne.like;

import com.project.plantOne.post.*;
import com.project.plantOne.user.User;
import com.project.plantOne.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {LikesServiceImpl.class})
@ExtendWith(SpringExtension.class)
class LikesServiceImplTest {
    @MockBean
    private PostRepository postRepository;

    @Mock
    PlantTypeRepository plantTypeRepository;

    @Mock
    PostTypeRepository postTypeRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private LikesRepository likesRepository;

    @Autowired
    private LikesServiceImpl likesServiceImpl;

    @Test
    void postLikeClickTest() {

        PostObjects postObjects = new PostObjects();

        User user = postObjects.getSellerUserObjects();
        Post post = postObjects.getPostObjects();
        post.setSeller(user);
        post.setLikesCount(0);


        User user1 = postObjects.getBuyerUserObjects();

        Likes like = new Likes();
        like.setPost(post);
        like.setUser(user1);
        like.setLikeId(UUID.randomUUID());
        when(this.likesRepository.getLikeIdByUserAndPost((UUID) any(), (UUID) any())).thenReturn(UUID.randomUUID());
        doNothing().when(this.likesRepository).deleteById((UUID) any());
        when(this.likesRepository.isUserAlreadyLiked((UUID) any(), (UUID) any())).thenReturn(1);
        when(this.likesRepository.save((Likes) any())).thenReturn(like);

        User user2 = postObjects.getBuyerUserObjects();
        when(this.userRepository.getById((UUID) any())).thenReturn(user2);

        User user3 = postObjects.getSellerUserObjects();
        Post post1 = postObjects.getPostObjects();
        post1.setLikesCount(0);
        post1.setSeller(user3);

        User user4 = postObjects.getSellerUserObjects();
        Post post2 = postObjects.getPostObjects();
        post2.setSeller(user4);
        post2.setLikesCount(0);

        when(this.postRepository.save((Post) any())).thenReturn(post2);
        when(this.postRepository.getById((UUID) any())).thenReturn(post1);
        UUID blogUUID = UUID.randomUUID();
        assertEquals("Like removed successfully", this.likesServiceImpl.likesClick(blogUUID, UUID.randomUUID()));
        verify(this.likesRepository).isUserAlreadyLiked((UUID) any(), (UUID) any());
        verify(this.likesRepository).getLikeIdByUserAndPost((UUID) any(), (UUID) any());
        verify(this.likesRepository).deleteById((UUID) any());
        verify(this.userRepository).getById((UUID) any());
        verify(this.postRepository).getById((UUID) any());
        verify(this.postRepository).save((Post) any());
    }

    @Test
    void testUpVoteClick2() {
        PostObjects postObjects = new PostObjects();

        User user = postObjects.getSellerUserObjects();
        Post post = postObjects.getPostObjects();
        post.setSeller(user);
        post.setLikesCount(0);


        User user1 = postObjects.getBuyerUserObjects();

        Likes like = new Likes();
        like.setPost(post);
        like.setUser(user1);
        like.setLikeId(UUID.randomUUID());
        when(this.likesRepository.getLikeIdByUserAndPost((UUID) any(), (UUID) any())).thenReturn(UUID.randomUUID());
        doNothing().when(this.likesRepository).deleteById((UUID) any());
        when(this.likesRepository.isUserAlreadyLiked((UUID) any(), (UUID) any())).thenReturn(0);
        when(this.likesRepository.save((Likes) any())).thenReturn(like);

        User user2 = postObjects.getBuyerUserObjects();
        when(this.userRepository.getById((UUID) any())).thenReturn(user2);

        User user3 = postObjects.getSellerUserObjects();
        Post post1 = postObjects.getPostObjects();
        post1.setLikesCount(0);
        post1.setSeller(user3);

        User user4 = postObjects.getSellerUserObjects();
        Post post2 = postObjects.getPostObjects();
        post2.setSeller(user4);
        post2.setLikesCount(0);

        when(this.postRepository.save((Post) any())).thenReturn(post2);
        when(this.postRepository.getById((UUID) any())).thenReturn(post1);
        UUID blogUUID = UUID.randomUUID();
        assertEquals("Liked successfully", this.likesServiceImpl.likesClick(blogUUID, UUID.randomUUID()));
        verify(this.likesRepository).isUserAlreadyLiked((UUID) any(), (UUID) any());
        verify(this.likesRepository).save((Likes) any());
        verify(this.userRepository).getById((UUID) any());
        verify(this.postRepository).getById((UUID) any());
        verify(this.postRepository).save((Post) any());
    }
}

