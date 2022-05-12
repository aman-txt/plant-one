package com.project.plantOne.vote;


import com.project.plantOne.blog.Blog;
import com.project.plantOne.blog.BlogRepository;
import com.project.plantOne.user.User;
import com.project.plantOne.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class VoteServiceImpl implements VoteService{

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public String upVoteClick(UUID blogUUID, UUID userUUID) {

        Blog blog = blogRepository.getById(blogUUID);
        User user = userRepository.getById(userUUID);
        Vote vote = new Vote(blog,user);
        String result;

        if(voteRepository.isUserAlreadyUpvoted(blogUUID,userUUID)>0)
        {
            voteRepository.deleteById(voteRepository.getVoteIdByUserAndBlog(blogUUID, userUUID));
            blog.setVoteCount(blog.getVoteCount() - 1);
            result = "Upvote removed successfully";
        }else
        {
            voteRepository.save(vote);
            blog.setVoteCount(blog.getVoteCount() + 1);
            result = "Upvoted successfully";
        }

        blogRepository.save(blog);
        return result;
    }

}
