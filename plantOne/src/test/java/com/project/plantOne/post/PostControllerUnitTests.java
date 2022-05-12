package com.project.plantOne.post;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.project.plantOne.user.User;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.project.plantOne.constants.Constants.POST_URL;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
@ComponentScan(basePackages = "com.project.plantOne.post")
public class PostControllerUnitTests {

    @Configuration
    @EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
    static class ContextConfiguration { }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostServiceImpl postService;

    @Test
    public void getAllPostsTest() throws Exception {

        String uri = POST_URL + "/allPosts/{user_id}";

        List<PostResponse> postResponseList = new ArrayList<>();
        List<Post> postList = new ArrayList<>();

        PostObjects postObjects = new PostObjects();
        Post post = postObjects.getPostObjects();
        PostResponse postResponse = postObjects.getPostResponseObject();
        User user = postObjects.getSellerUserObjects();

        postResponseList.add(postResponse);
        postList.add(post);

        Mockito.when(postService.getPostList(post.getSeller().getUser_id())).thenReturn(postResponseList);

        mockMvc.perform(get(uri,post.getSeller().getUser_id()).content(objectMapper.writeValueAsString(postList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title").value(postList.get(0).getTitle()));

    }

    @Test
    public void getPostTest() throws Exception{

        String uri = POST_URL + "/{post_id}";

        PostObjects postObjects = new PostObjects();
        Post post = postObjects.getPostObjects();

        Mockito.when(postService.getPost(any(UUID.class))).thenReturn(post);

        mockMvc.perform(get(uri,post.getPostUUID()).content(objectMapper.writeValueAsString(post))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(post.getTitle()));

    }

    @Test
    public void addPostTest() throws Exception {

        String uri = POST_URL + "/add";

        PostObjects postObjects = new PostObjects();
        Post post = postObjects.getPostObjects();
        MockMultipartFile file = new MockMultipartFile("file","hello.txt",MediaType.TEXT_PLAIN_VALUE,"Hello, World!".getBytes());
        MockMultipartFile postFile = new MockMultipartFile("post",null,MediaType.APPLICATION_JSON_VALUE,objectMapper.writeValueAsString(post)
                .getBytes());


        Mockito.when(postService.addPost(any(Post.class),any(MultipartFile.class))).thenReturn(post);

        mockMvc.perform(multipart(uri)
                        .file(file)
                        .file(postFile))
                .andExpect(status().isOk());
    }


    @Test
    public void getPlantTypeTest() throws Exception{

        String uri = POST_URL + "/getPlantType";

        PostObjects postObjects = new PostObjects();

        List<PlantType> plantTypeList = postObjects.getPlantType();

        Mockito.when(postService.getPlantType()).thenReturn(plantTypeList);

        mockMvc.perform(get(uri).content(objectMapper.writeValueAsString(plantTypeList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getPostTypeTest() throws Exception{

        String uri = POST_URL + "/getPostType";

        PostObjects postObjects = new PostObjects();

        List<PostType> posTypeList = postObjects.getPostType();

        Mockito.when(postService.getPostType()).thenReturn(posTypeList);

        mockMvc.perform(get(uri).content(objectMapper.writeValueAsString(posTypeList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


}
