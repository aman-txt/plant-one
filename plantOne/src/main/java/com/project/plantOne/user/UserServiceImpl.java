package com.project.plantOne.user;

import com.project.plantOne.exception.GlobalException;
import com.project.plantOne.user.customUserModel.UserRequest;
import com.project.plantOne.user.customUserModel.UserResponse;
import com.project.plantOne.user.customUserModel.UserUpdateRequest;
import com.project.plantOne.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse addUser(UserRequest userRequest, MultipartFile file) throws IOException {
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User user = CustomModelMapper.createRequestModelMapper(userRequest,file);
        User returnUser = userRepository.save(user);
        UserResponse userResponse = CustomModelMapper.responseModelMapper(returnUser);
        return userResponse;
    }

    public UserResponse getUserById(UUID user_id) {
        User user;
        UserResponse userResponse;
        if (userRepository.findById(user_id).isPresent()) {
            user = userRepository.findById(user_id).get();
            userResponse = CustomModelMapper.responseModelMapper(user);
        } else {
            throw new GlobalException("User id does not exists.",false);
        }
        return userResponse;
    }

    public UserResponse updateUser(UserUpdateRequest userUpdateRequest, MultipartFile file) throws IOException {
        User originalUser;
        UserResponse userResponse;

        if (userRepository.findById(userUpdateRequest.getUser_id()).isPresent()) {
            originalUser = userRepository.findById(userUpdateRequest.getUser_id()).get();
            originalUser = CustomModelMapper.updateRequestModelMapper(userUpdateRequest,originalUser,file);
            originalUser = userRepository.save(originalUser);
            userResponse = CustomModelMapper.responseModelMapper(originalUser);
        } else {
            throw new GlobalException("User does not exists.",false);
        }
        return userResponse;
    }
    public String deleteUserById(UUID user_id) throws EmptyResultDataAccessException{
        String result;
        try{
            userRepository.deleteById(user_id);
            result = "User deleted successfully";
        }catch(Exception exception){
            result = "Exception:"+exception;
        }
        return result;
    };

    @Transactional(propagation = Propagation.MANDATORY)
    public UserResponse getUserByEmail(String email){
        User user = userRepository.findByEmail(email);
        UserResponse userResponse;
        if (user != null) {
            userResponse = CustomModelMapper.responseModelMapper(user);
            return userResponse;
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }

}
