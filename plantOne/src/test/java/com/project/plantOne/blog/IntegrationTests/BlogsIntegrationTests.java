package com.project.plantOne.blog.IntegrationTests;


import com.project.plantOne.AbstractTest;
import com.project.plantOne.PlantOneApplication;
import com.project.plantOne.blog.Blog;
import com.project.plantOne.blog.BlogObjects;
import com.project.plantOne.blog.BlogRepository;
import com.project.plantOne.user.User;
import com.project.plantOne.user.UserObjects;
import com.project.plantOne.user.UserRepository;
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

import static com.project.plantOne.constants.Constants.BLOG_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PlantOneApplication.class)
@WebAppConfiguration
@EnableWebMvc
public class BlogsIntegrationTests extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }
    int expectedStatus = 200;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BlogRepository blogRepository;

    @Test
    public void addBlogTest() throws Exception{

        String uri = BLOG_URL + "/";
        UserObjects userObjects = new UserObjects();
        User user = userObjects.getUser();
        //userRepository.save(user);
        BlogObjects blogObjects = new BlogObjects();
        Blog blog = blogObjects.getBlogObjects(user);


        String inputJson = super.mapToJson(blog);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatus, status);
        blogRepository.delete(blog);

    }

    @Test
    public void getBlogsTest() throws Exception {
        UserObjects userObjects = new UserObjects();
        User user = userObjects.getUser();
        //userRepository.save(user);
        BlogObjects blogObjects = new BlogObjects();
        Blog blog = blogObjects.getBlogObjects(user);
        //blogRepository.save(blog);
        String uri = BLOG_URL + "/getAllBlogs/"+userRepository.findAll().get(0).getUser_id();
        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(uri)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        blogRepository.delete(blog);

    }

    @Test
    public void getBlogTest() throws Exception{

        BlogObjects blogObjects = new BlogObjects();
        UserObjects userObjects = new UserObjects();
        User user = userObjects.getUser();
        //userRepository.save(user);
        String uri = BLOG_URL + "/{blogUUID}";
        Blog blog = blogObjects.getBlogObjects(blogObjects.getUserObjects());
        User tempUser  = userRepository.findAll().get(0);
        blog.setUser(tempUser);
        blogRepository.save(blog);
        Blog savedBlog = blogRepository.findAll().get(0);

        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(uri,savedBlog.getBlogUUID())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        blogRepository.delete(blog);
        //userRepository.delete(tempUser);
    }


}
