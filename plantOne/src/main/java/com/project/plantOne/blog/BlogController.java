package com.project.plantOne.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.project.plantOne.constants.Constants.BLOG_URL;

@RestController
@RequestMapping(path = BLOG_URL)
public class BlogController {

    @Autowired
    private BlogServiceImpl blogServiceImpl;

    @GetMapping(value="/{blogUUID}")
    @ResponseBody
    public Blog getBlog(@PathVariable("blogUUID") UUID blogUUID){
        return blogServiceImpl.getBlog(blogUUID);
    }

    @GetMapping(value="/getAllBlogs/{user_id}")
    @ResponseBody
    public List<BlogResponse> getBlogsByUser(@PathVariable("user_id") UUID user_id){
        return blogServiceImpl.getBlogsByUser(user_id);
    }

    @PostMapping(value="/")
    public Blog addBlog(@RequestBody Blog blog){
        return blogServiceImpl.addBlog(blog);
    }

    @DeleteMapping(value="/{blogUUID}")
    public Blog deleteBlogById(@PathVariable("blogUUID") UUID blogUUID){
        return blogServiceImpl.deleteBlogById(blogUUID);
    }

    @PutMapping(value="/")
    public Blog updateBlog(@RequestBody Blog blog){
        return blogServiceImpl.updateBlog(blog);
    }
}




