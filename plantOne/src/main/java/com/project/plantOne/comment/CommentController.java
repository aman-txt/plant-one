package com.project.plantOne.comment;

import com.project.plantOne.post.Post;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.project.plantOne.constants.Constants.COMMENT_URL;

@RestController
@RequestMapping(value = COMMENT_URL)
public class CommentController {

    @Autowired
    CommentServiceImpl commentService;


    @PostMapping(value = "/add/{post_id}")
    public Post addComment(@PathVariable ("post_id") UUID postUUID, @RequestBody Comment comment) {
        commentService.addComment(postUUID, comment);

        return comment.getPost();
    }


    @DeleteMapping(value = "/{comment_id}")
    public void deleteComment(@PathVariable("comment_id") UUID commentUUID){

        commentService.deleteComment(commentUUID);
    }

}
