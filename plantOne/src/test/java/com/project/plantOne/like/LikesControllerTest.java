package com.project.plantOne.like;

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

import java.util.UUID;

import static com.project.plantOne.constants.Constants.LIKE_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EventController.class)
@AutoConfigureMockMvc(addFilters = false)
@ComponentScan(basePackages = "com.project.plantOne.like")
class LikesControllerTest {

    @Configuration
    @EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
    static class ContextConfiguration { }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LikeService likesService;

    @MockBean
    private LikesServiceImpl likesServiceImpl;

    @Test
    void postLikeClickTest() throws Exception {

        String uri = LIKE_URL + "/"+UUID.randomUUID()+"/"+UUID.randomUUID();

        Mockito.when(likesServiceImpl.likesClick(UUID.randomUUID(),UUID.randomUUID())).thenReturn("Liked successfully");

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.TEXT_PLAIN_VALUE)).andDo(print())
                .andExpect(status().isOk());

    }
}

