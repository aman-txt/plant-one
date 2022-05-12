package com.project.plantOne.post;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.plantOne.user.customUserModel.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.project.plantOne.constants.Constants.POST_URL;

@RestController
@RequestMapping(path = POST_URL)
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @GetMapping(value = "/allPosts/{user_id}")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public List<PostResponse> getAllPosts(@PathVariable ("user_id") UUID userUUID) {

        return postService.getPostList(userUUID);

    }

    @PostMapping(value = "/add", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void addPost(@RequestPart("post") Post post, @RequestPart(name ="file",required=false) MultipartFile file) throws IOException {

        postService.addPost(post, file);
    }

    @GetMapping(value = "/{post_id}")
    @ResponseBody
    public Post getPost(@PathVariable ("post_id") UUID postUUID) {

        return  postService.getPost(postUUID);
    }

    @GetMapping(value = "/getPlantType")
    @ResponseBody
    public List<PlantType> getPlantType(){

        return postService.getPlantType();
    }

    @GetMapping(value = "/getPostType")
    @ResponseBody
    public List<PostType> getPostType(){

        return postService.getPostType();
    }

    public void checkPlantType() {

        postService.checkPlantTypeExists();
        postService.checkPostTypeExists();
    }

    @DeleteMapping(value="/{post_id}")
    public String deletePostById(@PathVariable("post_id") UUID postUUID){
        return postService.deletePostById(postUUID);
    }

}
