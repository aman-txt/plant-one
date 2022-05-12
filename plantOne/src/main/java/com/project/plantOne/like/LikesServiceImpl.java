package com.project.plantOne.like;

import com.project.plantOne.post.Post;
import com.project.plantOne.post.PostRepository;
import com.project.plantOne.user.User;
import com.project.plantOne.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class LikesServiceImpl implements  LikeService{

    @Autowired
    LikesRepository likesRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public String likesClick(UUID postUUID, UUID userUUID) {

        Post post = postRepository.getById(postUUID);
        User user = userRepository.getById(userUUID);
        Likes likes = new Likes(post,user);
        String result;

        if(likesRepository.isUserAlreadyLiked(postUUID,userUUID)>0)
        {
            likesRepository.deleteById(likesRepository.getLikeIdByUserAndPost(postUUID, userUUID));
            post.setLikesCount(post.getLikesCount() - 1);
            result = "Like removed successfully";
        }else
        {
            likesRepository.save(likes);
            post.setLikesCount(post.getLikesCount() + 1);
            result = "Liked successfully";
        }

        postRepository.save(post);
        return result;
    }
}