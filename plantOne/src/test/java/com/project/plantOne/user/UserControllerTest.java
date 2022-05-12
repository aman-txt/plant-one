package com.project.plantOne.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.plantOne.jwt.JwtUtil;
import com.project.plantOne.user.customUserModel.UserRequest;
import com.project.plantOne.user.customUserModel.UserResponse;
import com.project.plantOne.user.customUserModel.UserUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.project.plantOne.constants.Constants.ADD_USER;
import static com.project.plantOne.constants.Constants.HOME_URL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @Configuration
    @EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
    static class ContextConfiguration { }

    @Test
    void shouldGetDefaultWelcomeMessage() throws Exception {
        this.mockMvc.perform(get(HOME_URL))
                .andDo(print())
                .andExpect(content().string("<h1>Welcome to PlantOne</h1>"));
    }

    @Test
    void shouldCreateUser() throws Exception{
        UserObjects userObjects = new UserObjects();
        UserRequest userRequest = userObjects.getUserRequest();
        UserResponse userResponse = userObjects.getUserResponse();
        MockMultipartFile file = new MockMultipartFile("file","hello.txt",MediaType.TEXT_PLAIN_VALUE,"Hello, World!".getBytes());
        MockMultipartFile user = new MockMultipartFile("user",null,MediaType.APPLICATION_JSON_VALUE,objectMapper.writeValueAsString(userRequest).getBytes());

        when(userService.addUser(any(UserRequest.class),any(MultipartFile.class))).thenReturn(userResponse);

        mockMvc.perform(multipart(ADD_USER)
                .file(file)
                .file(user))
                .andExpect(status().isOk()).andExpect(content().string(objectMapper.writeValueAsString(userResponse)));

    }

    @Test
    void shouldUpdateUser() throws Exception{

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart(ADD_USER);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        UserObjects userObjects = new UserObjects();
        UserUpdateRequest userUpdateRequest = userObjects.getUpdatedUser();
        UserResponse userResponse = userObjects.getUserResponse();
        MockMultipartFile file = new MockMultipartFile("file","hello.txt",MediaType.TEXT_PLAIN_VALUE,"Hello, World!".getBytes());
        MockMultipartFile user = new MockMultipartFile("user",null,MediaType.APPLICATION_JSON_VALUE,objectMapper.writeValueAsString(userUpdateRequest).getBytes());

        when(userService.updateUser(any(UserUpdateRequest.class),any(MultipartFile.class))).thenReturn(userResponse);

        mockMvc.perform(builder
                .file(file)
                .file(user))
                .andExpect(status().isOk()).andExpect(content().string(objectMapper.writeValueAsString(userResponse)));

    }

    @Test
    void shouldGetUserById() throws Exception {

        String uri = ADD_USER + "/{user_id}";
        UserObjects userObjects = new UserObjects();
        UserResponse userResponse = userObjects.getUserResponse();

        when(userService.getUserById(any(UUID.class))).thenReturn(userResponse);

        mockMvc.perform(get(uri,UUID.randomUUID().toString()))
                .andExpect(status().isOk()).andExpect(content().string(objectMapper.writeValueAsString(userResponse)));
    }

    @Test
    void shouldDeleteUserById() throws Exception {

        String uri = ADD_USER + "/{user_id}";

        when(userService.deleteUserById(any(UUID.class))).thenReturn("User deleted successfully");

        mockMvc.perform(delete(uri,UUID.randomUUID().toString()))
                .andExpect(status().isOk()).andExpect(content().string("User deleted successfully"));
    }

    @Test
    void shouldGetUserByEmail() throws Exception {

        String uri = ADD_USER + "/email/{email}";
        UserObjects userObjects = new UserObjects();
        String email = userObjects.getUser().getEmail();
        UserResponse userResponse = userObjects.getUserResponse();
        when(userService.getUserByEmail(any(String.class))).thenReturn(userResponse);

        mockMvc.perform(get(uri,email))
                .andExpect(status().isOk()).andExpect(content().string(objectMapper.writeValueAsString(userResponse)));
    }

}
