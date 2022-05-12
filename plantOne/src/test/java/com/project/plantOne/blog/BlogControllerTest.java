package com.project.plantOne.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.plantOne.event.EventController;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.project.plantOne.constants.Constants.BLOG_URL;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(EventController.class)
@AutoConfigureMockMvc(addFilters = false)
@ComponentScan(basePackages = "com.project.plantOne.blog")
class BlogControllerTest {

    @Configuration
    @EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
    static class ContextConfiguration { }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BlogService blogService;

    @MockBean
    private BlogServiceImpl blogServiceImpl;

    @Test
    public void getBlogsTest() throws Exception {


        String uri = BLOG_URL + "/getAllBlogs/" + UUID.randomUUID().toString();
        List<BlogResponse> blogList = new ArrayList<>();

        BlogObjects blogObjects = new BlogObjects();
        Blog blog = blogObjects.getBlogObjects(blogObjects.getUserObjects());

        blogList.add(blogObjects.blogResponseModelMapper(blog));
        Mockito.when(blogServiceImpl.getBlogsByUser(UUID.randomUUID())).thenReturn(blogList);

        mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getBlog() throws Exception{

        String uri = BLOG_URL + "/" + UUID.randomUUID();

        BlogObjects blogObjects = new BlogObjects();
        Blog blog = blogObjects.getBlogObjects(blogObjects.getUserObjects());

        Mockito.when(blogServiceImpl.getBlog(any(UUID.class))).thenReturn(blog);

        mockMvc.perform(get(uri,blog.getBlogUUID()).content(objectMapper.writeValueAsString(blog))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addBlogTest() throws Exception {

        String uri = BLOG_URL + "/";

        BlogObjects blogObjects = new BlogObjects();
        Blog blog = blogObjects.getBlogObjects(blogObjects.getUserObjects());

        Mockito.when(blogServiceImpl.addBlog(any(Blog.class))).thenReturn(blog);

        mockMvc.perform(post(uri).content(objectMapper.writeValueAsString(blog))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void deleteBlogTest() throws Exception {

        String uri = BLOG_URL + "/" + UUID.randomUUID();

        BlogObjects blogObjects = new BlogObjects();
        Blog blog = blogObjects.getBlogObjects(blogObjects.getUserObjects());

        Mockito.when(blogServiceImpl.deleteBlogById(UUID.randomUUID())).thenReturn(blog);

        mockMvc.perform(delete(uri).content(objectMapper.writeValueAsString(blog))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateBlogTest() throws Exception {

        String uri = BLOG_URL + "/";

        BlogObjects blogObjects = new BlogObjects();
        Blog blog = blogObjects.getBlogObjects(blogObjects.getUserObjects());

        Mockito.when(blogServiceImpl.updateBlog(any(Blog.class))).thenReturn(blog);

        mockMvc.perform(put(uri).content(objectMapper.writeValueAsString(blog))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
