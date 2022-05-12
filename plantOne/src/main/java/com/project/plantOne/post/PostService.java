package com.project.plantOne.post;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface PostService {

    public List<PostResponse> getPostList(UUID userUUID);

    Post addPost(Post post, MultipartFile file ) throws IOException;

    public Post getPost(UUID postUUID);

    public int checkPlantTypeExists();

    public int checkPostTypeExists();

    public String deletePostById(UUID postUUID);

    public List<PlantType> getPlantType();

    public List<PostType> getPostType();
}
