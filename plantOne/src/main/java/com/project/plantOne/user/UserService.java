package com.project.plantOne.user;

import com.project.plantOne.user.customUserModel.UserRequest;
import com.project.plantOne.user.customUserModel.UserResponse;
import com.project.plantOne.user.customUserModel.UserUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface UserService {

    public UserResponse addUser(UserRequest userRequest, MultipartFile file) throws IOException;

    public UserResponse updateUser(UserUpdateRequest user, MultipartFile file)throws IOException;

    public String deleteUserById(UUID user_id);

    public UserResponse getUserById(UUID user_id);

    public UserResponse getUserByEmail(String email);


}
