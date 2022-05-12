package com.project.plantOne.user;

import com.project.plantOne.user.customUserModel.UserRequest;
import com.project.plantOne.user.customUserModel.UserResponse;
import com.project.plantOne.user.customUserModel.UserUpdateRequest;
import com.project.plantOne.util.CustomModelMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Test
    void testAddUser() throws IOException {
        UserObjects userObjects = new UserObjects();
        UserRequest userRequest = userObjects.getUserRequest();
        MockMultipartFile file = new MockMultipartFile("file","hello.txt", MediaType.TEXT_PLAIN_VALUE,"Hello, World!".getBytes());
        User user = CustomModelMapper.createRequestModelMapper(userRequest,file);
        Mockito.when(this.userRepository.save((User) any())).thenReturn(user);
        UserResponse userResponse = userService.addUser(userRequest,file);
        System.out.println(userResponse.getFirst_name());
        assertThat(userResponse.getFirst_name()).isSameAs(userRequest.getFirst_name());
    }

    @Test
    void testGetUserById() throws UnsupportedEncodingException {
        UserObjects userObjects = new UserObjects();
        User user = userObjects.getUser();
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById((UUID) any())).thenReturn(ofResult);
        UserResponse actualUserById = this.userService.getUserById(UUID.randomUUID());
        assertEquals(user.getUsername(), actualUserById.getUsername());
        verify(this.userRepository, atLeast(1)).findById((UUID) any());
    }
    @Test
    void testUpdateUser() throws IOException {
        UserObjects userObjects = new UserObjects();
        User user = userObjects.getUser();
        Optional<User> ofResult = Optional.of(user);

        User user1 = userObjects.getUser();
        when(this.userRepository.save((User) any())).thenReturn(user1);
        when(this.userRepository.findById((UUID) any())).thenReturn(ofResult);

        UserUpdateRequest userUpdateRequest = userObjects.getUpdatedUser();
        MockMultipartFile file = new MockMultipartFile("file","hello.txt", MediaType.TEXT_PLAIN_VALUE,"Hello, World!".getBytes());
        UserResponse actualUpdateUserResult = this.userService.updateUser(userUpdateRequest,file);
        assertEquals(user.getUsername(), actualUpdateUserResult.getUsername());

        verify(this.userRepository, atLeast(1)).findById((UUID) any());
        verify(this.userRepository).save((User) any());
    }

    @Test
    void testDeleteUserById() throws EmptyResultDataAccessException {
        doNothing().when(this.userRepository).deleteById((UUID) any());
        assertEquals("User deleted successfully", this.userService.deleteUserById(UUID.randomUUID()));
        verify(this.userRepository).deleteById((UUID) any());
    }

    @Test
    void testGetUserByEmail() throws UnsupportedEncodingException {
        UserObjects userObjects = new UserObjects();
        User user = userObjects.getUser();
        when(this.userRepository.findByEmail((String) any())).thenReturn(user);
        assertSame(user.getEmail(), this.userService.getUserByEmail(user.getEmail()).getEmail());
        verify(this.userRepository).findByEmail((String) any());
    }

}
