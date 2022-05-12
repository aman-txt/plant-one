package com.project.plantOne.post;


import com.project.plantOne.like.LikesRepository;
import com.project.plantOne.user.User;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class PostServiceImplUnitTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostServiceImpl postService;

    @Mock
    PlantTypeRepository plantTypeRepository;

    @Mock
    PostTypeRepository postTypeRepository;

    @Mock
    LikesRepository likesRepository;

    static final int expectedRowsInserted = 0;
    static final int postReturnTableSize = 3;
    static final int plantReturnTableSize = 5;

    @Test
    public void getPostsTest() {

        List<PostResponse> postResponseList = new ArrayList<>();
        List<Post> postList = new ArrayList<>();

        PostObjects postObjects = new PostObjects();
        Post post = postObjects.getPostObjects();
        PostResponse postResponse = postObjects.getPostResponseObject();
        User user = postObjects.getSellerUserObjects();

        postResponseList.add(postResponse);
        postList.add(post);

        Mockito.when(postRepository.getActivePosts(post.getBuyer().getUser_id())).thenReturn(postList);
        Mockito.when(likesRepository.isUserAlreadyLiked(Mockito.any(UUID.class),Mockito.any(UUID.class))).thenReturn(0);
        List<PostResponse> responsePostList = postService.getPostList(post.getBuyer().getUser_id());
        assertThat(responsePostList.get(0).getTitle()).isSameAs(postList.get(0).getTitle());

    }

    @Test
    public void getPostTest(){


        List<Post> postList = new ArrayList<>();

        PostObjects postObjects = new PostObjects();
        Post post = postObjects.getPostObjects();

        Mockito.when(postRepository.findById(post.getPostUUID())).thenReturn(Optional.of(post));
        Post responsePostList = postService.getPost(post.getPostUUID());
        assertEquals(responsePostList.getPrice(),post.getPrice());

    }

    @Test
    public void addPostTest() throws IOException {

        PostObjects postObjects = new PostObjects();

        Post post = postObjects.getPostObjects();

        Mockito.when(postRepository.save(any(Post.class))).thenReturn(post);
        Post responsePost = postService.addPost(post, any(MultipartFile.class));
        assertThat(responsePost.isIs_archive()).isSameAs(responsePost.isIs_archive());
        assertThat(responsePost.getComment()).isSameAs(post.getComment());

    }

    @Test
    public void checkPlantTypeExists(){


        Mockito.when(plantTypeRepository.findPlantTypeTableSize()).thenReturn(plantReturnTableSize);
        int actualRowsInserted = postService.checkPlantTypeExists();
        assertEquals(expectedRowsInserted, actualRowsInserted);

    }

    @Test
    public void checkPosTypeExists(){


        Mockito.when(postTypeRepository.findPostTypeTableSize()).thenReturn(postReturnTableSize);
        int actualRowsInserted = postService.checkPostTypeExists();
        assertEquals(expectedRowsInserted, actualRowsInserted);

    }


    @Test
    public void getPlantTypeTest(){

        PostObjects postObject = new PostObjects();
        List<PlantType> plantTypeList = postObject.getPlantType();

        Mockito.when(plantTypeRepository.findAll()).thenReturn(plantTypeList);
        List<PlantType> responsePlantTypeList = postService.getPlantType();
        assertThat(responsePlantTypeList).isSameAs(plantTypeList);
    }

    @Test
    public void getPostTypeTest(){

        PostObjects postObject = new PostObjects();
        List<PostType> postTypeList = postObject.getPostType();

        Mockito.when(postTypeRepository.findAll()).thenReturn(postTypeList);
        List<PostType> responsePostTypeList = postService.getPostType();
        assertThat(responsePostTypeList).isSameAs(postTypeList);
    }



}
