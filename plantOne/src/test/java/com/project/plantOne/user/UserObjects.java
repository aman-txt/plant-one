package com.project.plantOne.user;

import com.project.plantOne.user.customUserModel.UserRequest;
import com.project.plantOne.user.customUserModel.UserResponse;
import com.project.plantOne.user.customUserModel.UserUpdateRequest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UserObjects {
    public UUID userId;
    static int randomStringLength = 4;
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Date createDate(){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try{
            date = formatter.parse("2020-12-12");
        }catch(ParseException parseException){
            System.out.println("Parse Exception in user creation entity in UserObjects class"+parseException);
        }
        return date;
    }

    public UserRequest getUserRequest() {

        UserRequest userRequest = new UserRequest();
        userRequest.setFirst_name("Testing");
        userRequest.setLast_name("PlantOne");
        userRequest.setPassword("password");
        userRequest.setUser_role("user");
        userRequest.setUsername(null);
        userRequest.setEmail("testIntegration@plantone.com");
        userRequest.setStreet("street");
        userRequest.setCity("city");
        userRequest.setCountry("country");
        userRequest.setPostal_code("postalCode");
        userRequest.setDob(createDate());

        return userRequest;
    }

    public UserResponse getUserResponse() {
        UserResponse userResponse = new UserResponse();
        userResponse.setUser_id(UUID.randomUUID());
        userResponse.setFirst_name("Testing");
        userResponse.setLast_name("PlantOne");
        userResponse.setUser_role("user");
        userResponse.setEmail("testIntegration@plantone.com");
        userResponse.setStreet("street");
        userResponse.setCity("city");
        userResponse.setCountry("country");
        userResponse.setPostal_code("postalCode");
        userResponse.setDob(createDate());

        return userResponse;
    }

    public User getUser() {
        User user = new User();
        user.setUser_id(UUID.randomUUID());
        user.setFirst_name("Testing");
        user.setLast_name("PlantOne");
        user.setPassword("password");
        user.setActiveUser(true);
        user.setUsername(null);
        user.setUser_role("user");
        user.setEmail("testIntegration@plantone.com");
        user.setStreet("street");
        user.setCity("city");
        user.setCountry("country");
        user.setPostal_code("postalCode");
        user.setDob(createDate());
        return user;
    }

    public UserUpdateRequest getUpdatedUser(){
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUser_id(UUID.randomUUID());
        userUpdateRequest.setFirst_name("Testing");
        userUpdateRequest.setLast_name("PlantOne");
        userUpdateRequest.setUser_role("user");
        userUpdateRequest.setStreet("street");
        userUpdateRequest.setCity("city");
        userUpdateRequest.setCountry("country");
        userUpdateRequest.setPostal_code("postalCode");
        userUpdateRequest.setDob(createDate());
        return userUpdateRequest;
    }


}
