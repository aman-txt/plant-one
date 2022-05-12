package com.project.plantOne.vote;

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

import static com.project.plantOne.constants.Constants.VOTE_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EventController.class)
@AutoConfigureMockMvc(addFilters = false)
@ComponentScan(basePackages = "com.project.plantOne.vote")
class VoteControllerTest {

    @Configuration
    @EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
    static class ContextConfiguration { }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VoteService voteService;

    @MockBean
    private VoteServiceImpl voteServiceImpl;

    @Test
    void upVoteClickTest() throws Exception {

        String uri = VOTE_URL + "/"+UUID.randomUUID()+"/"+UUID.randomUUID();

        Mockito.when(voteServiceImpl.upVoteClick(UUID.randomUUID(),UUID.randomUUID())).thenReturn("Upvote removed successfully");

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.TEXT_PLAIN_VALUE)).andDo(print())
                .andExpect(status().isOk());

    }
}

