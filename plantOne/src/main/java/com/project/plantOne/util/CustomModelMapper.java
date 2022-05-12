package com.project.plantOne.util;

import com.project.plantOne.blog.Blog;
import com.project.plantOne.blog.BlogResponse;
import com.project.plantOne.post.Post;
import com.project.plantOne.post.PostResponse;
import com.project.plantOne.user.User;
import com.project.plantOne.user.customUserModel.UserRequest;
import com.project.plantOne.user.customUserModel.UserResponse;
import com.project.plantOne.user.customUserModel.UserUpdateRequest;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public class CustomModelMapper {

    public static User createRequestModelMapper(UserRequest userRequest,MultipartFile file) throws IOException {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userRequest,User.class);
        user.setActiveUser(true);
        user.setUser_role("user");
        if(file!=null){
            user.setProfilePicture(file.getBytes());
            user.setFileExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
        }
        return user;
    }

    public static User updateRequestModelMapper(UserUpdateRequest userUpdateRequest , User originalUser, MultipartFile file) throws IOException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(userUpdateRequest,originalUser);
        if(file!=null){
            originalUser.setProfilePicture(file.getBytes());
            originalUser.setFileExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
        }
        return originalUser;
    }

    public static UserResponse responseModelMapper(User user){
        ModelMapper modelMapper = new ModelMapper();
        UserResponse userResponse = modelMapper.map(user,UserResponse.class);
        if(user.getProfilePicture()!=null){
            String encodeToString = "data:image/"+user.getFileExtension()+";base64," + Base64.getEncoder().encodeToString(user.getProfilePicture());
            userResponse.setBase64Image(encodeToString);
        }
        return userResponse;
    }

    public static BlogResponse blogResponseModelMapper(Blog blog){
        ModelMapper modelMapper = new ModelMapper();
        BlogResponse blogResponse = modelMapper.map(blog, BlogResponse.class);
        return blogResponse;
    }

    public static PostResponse postResponseModelMapper(Post post){
        ModelMapper modelMapper = new ModelMapper();
        PostResponse postResponse = modelMapper.map(post, PostResponse.class);
        return postResponse;
    }

}
