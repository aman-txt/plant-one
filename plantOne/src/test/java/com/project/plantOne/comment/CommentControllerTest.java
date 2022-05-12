package com.project.plantOne.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.plantOne.event.EventController;
import com.project.plantOne.post.Post;
import com.project.plantOne.post.PostObjects;
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

import java.util.UUID;

import static com.project.plantOne.constants.Constants.COMMENT_URL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EventController.class)
@AutoConfigureMockMvc(addFilters = false)
@ComponentScan(basePackages = "com.project.plantOne.comment")
class CommentControllerTest {

    @Configuration
    @EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
    static class ContextConfiguration { }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @MockBean
    private CommentServiceImpl commentServiceImpl;

    @Test
    void addCommentTest() throws Exception {

        String uri = COMMENT_URL + "/add/"+UUID.randomUUID();

        PostObjects postObjects = new PostObjects();

        Comment comment = new Comment();
        comment.setComment("Test");
        comment.setCommentUUID(UUID.randomUUID());

        comment.setUser(postObjects.getSellerUserObjects());
        comment.setPost(postObjects.getPostObjects());

        Post post = postObjects.getPostObjects();

        Mockito.when(commentServiceImpl.addComment(any(UUID.class),any(Comment.class))).thenReturn(comment);

        mockMvc.perform(MockMvcRequestBuilders.post(uri).content(objectMapper.writeValueAsString(post))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void deleteCommentTest() throws Exception {

        String uri = COMMENT_URL + "/"+UUID.randomUUID();

        doNothing().when(commentServiceImpl).deleteComment(any(UUID.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(uri)
                        .contentType(MediaType.TEXT_PLAIN_VALUE)).andDo(print())
                .andExpect(status().isOk());
    }
}

