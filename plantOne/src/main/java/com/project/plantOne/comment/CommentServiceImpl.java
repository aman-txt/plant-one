package com.project.plantOne.comment;

import com.project.plantOne.post.PostRepository;
import com.project.plantOne.user.User;
import com.project.plantOne.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    public Comment addComment(UUID postUUID, Comment comment){
        Optional<User> user = userRepository.findById(comment.getUser().getUser_id());
        comment.setUser(user.get());
        comment.setPost(postRepository.findBypostUUID(postUUID));
        return commentRepository.save(comment);
    }

    public void deleteComment(UUID commentUUID){

        commentRepository.deleteByCommentUUID(commentUUID);
    }
}
