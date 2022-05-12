package com.project.plantOne.post.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.plantOne.AbstractTest;

import com.project.plantOne.post.PostObjects;
import com.project.plantOne.post.Post;
import com.project.plantOne.post.PostRepository;
import com.project.plantOne.post.PostResponse;
import com.project.plantOne.user.User;
import com.project.plantOne.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.project.plantOne.constants.Constants.POST_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;


public class PostIntegrationTest extends AbstractTest {

     /*
        Integration Testing - PostController.java
        Author - Sarthak Pandit <sr215260@dal.ca>
     */

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;


    PostObjects postObjects = new PostObjects();
    User savedUser = new User();

    @Override
    @Before
    public void setUp() {

        User user  = postObjects.getBuyerUserObjects();
        User savedUser = userRepository.save(user);
        super.setUp();
    }

    int expectedStatus = 200;

    @Test
    public void getAllPostsTest() throws Exception{

        String uri = POST_URL + "/allPosts/{user_id}";

        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(uri,userRepository.findAll().get(0).getUser_id())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, status);
        String content = mvcResult.getResponse().getContentAsString();
        PostResponse[] postList = super.mapFromJson(content, PostResponse[].class);
        assertTrue(postList.length > 0);
    }

    @Test
    public void addPostTest() throws Exception{

        String uri = POST_URL + "/add";

        PostObjects postObjects = new PostObjects();
        Post post = postObjects.getPostObjects();
        post.setSeller(userRepository.findAll().get(0));

        MockMultipartFile file = new MockMultipartFile("file","hello.txt",MediaType.TEXT_PLAIN_VALUE,"Hello, World!".getBytes());
        MockMultipartFile postFile = new MockMultipartFile("post",null,MediaType.APPLICATION_JSON_VALUE,objectMapper.writeValueAsString(post)
                .getBytes());

        String inputJson = super.mapToJson(post);
        MvcResult mvcResult = mvc.perform(multipart(uri)
                        .file(file)
                        .file(postFile)
                 .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson)).andReturn();


        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, status);
    }

    @Test
    public void getPostTest() throws Exception{

        String uri = POST_URL + "/{post_id}";

        PostObjects postObjects = new PostObjects();
        Post post = postObjects.getPostObjects();
        post.setSeller(userRepository.findAll().get(0));
        postRepository.save(post);
        Post responsePost = postRepository.findAll().get(0);

        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(uri,responsePost.getPostUUID())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, status);

    }

    @Test
    public void getPlantTypeTest() throws Exception {

        String uri = POST_URL + "/getPlantType";

        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(uri)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, status);

    }

    @Test
    public void getPostTypeTest() throws Exception {

        String uri = POST_URL + "/getPostType";

        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(uri)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, status);

    }


}
