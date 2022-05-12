package com.project.plantOne.blog;

import java.util.List;
import java.util.UUID;

public interface BlogService {

    public Blog getBlog(UUID blogUUID);

    public Blog addBlog(Blog blog);

    public List<BlogResponse> getBlogsByUser(UUID user_id);

    public Blog deleteBlogById(UUID blogUUID);

    public Blog updateBlog(Blog blog);
}
