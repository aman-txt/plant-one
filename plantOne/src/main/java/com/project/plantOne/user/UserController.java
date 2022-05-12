package com.project.plantOne.user;

import com.project.plantOne.jwt.JwtRequest;
import com.project.plantOne.user.customUserModel.UserRequest;
import com.project.plantOne.user.customUserModel.UserResponse;
import com.project.plantOne.user.customUserModel.UserUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static com.project.plantOne.constants.Constants.USERS_URL;

@RestController
@RequestMapping(path=USERS_URL)
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(value="/home")
    public String login(){
        return ("<h1>Welcome to PlantOne</h1>");
    }

    @PostMapping(value="/",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public UserResponse addUser(@RequestPart("user") UserRequest userRequest,@RequestPart(name ="file",required=false) MultipartFile file) throws IOException {
        return userService.addUser(userRequest,file);
    }

    @PutMapping(value="/",consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public UserResponse updateUser(@RequestPart("user") UserUpdateRequest user,@RequestPart(name ="file",required=false) MultipartFile file) throws IOException {
        return userService.updateUser(user,file);
    }

    @GetMapping(value="/{user_id}")
    @ResponseBody
    public UserResponse getUserById(@PathVariable("user_id") UUID user_id){
        return userService.getUserById(user_id);
    }

    @DeleteMapping(value="/{user_id}")
    public String deleteUserById(@PathVariable("user_id") UUID user_id) {
        return userService.deleteUserById(user_id);
    }

    @GetMapping(value="/email/{email}")
    @ResponseBody
    public UserResponse getUserByEmail(@PathVariable("email") String email){
        return userService.getUserByEmail(email);
    }

    @GetMapping(value="/authenticate")
    @ResponseBody
    public void  authenticateUser(@RequestBody JwtRequest jwtRequest){
    }

}
