package com.project.plantOne.vote;

import com.project.plantOne.blog.Blog;
import com.project.plantOne.blog.BlogRepository;
import com.project.plantOne.user.User;
import com.project.plantOne.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {VoteServiceImpl.class})
@ExtendWith(SpringExtension.class)
class VoteServiceImplTest {
    @MockBean
    private BlogRepository blogRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private VoteRepository voteRepository;

    @Autowired
    private VoteServiceImpl voteServiceImpl;

    @Test
    void testUpVoteClick() {
        VoteObjects voteObjects = new VoteObjects();
        User user = voteObjects.getUserObjects();
        Blog blog = voteObjects.getBlogObjects(user);

        User user1 = voteObjects.getUserObjects();

        Vote vote = new Vote();
        vote.setBlog(blog);
        vote.setUser(user1);
        vote.setVoteId(UUID.randomUUID());
        when(this.voteRepository.getVoteIdByUserAndBlog((UUID) any(), (UUID) any())).thenReturn(UUID.randomUUID());
        doNothing().when(this.voteRepository).deleteById((UUID) any());
        when(this.voteRepository.isUserAlreadyUpvoted((UUID) any(), (UUID) any())).thenReturn(1);
        when(this.voteRepository.save((Vote) any())).thenReturn(vote);

        User user2 = voteObjects.getUserObjects();
        when(this.userRepository.getById((UUID) any())).thenReturn(user2);

        User user3 = voteObjects.getUserObjects();

        Blog blog1 = voteObjects.getBlogObjects(user3);

        User user4 = voteObjects.getUserObjects();

        Blog blog2 = voteObjects.getBlogObjects(user4);
        when(this.blogRepository.save((Blog) any())).thenReturn(blog2);
        when(this.blogRepository.getById((UUID) any())).thenReturn(blog1);
        UUID blogUUID = UUID.randomUUID();
        assertEquals("Upvote removed successfully", this.voteServiceImpl.upVoteClick(blogUUID, UUID.randomUUID()));
        verify(this.voteRepository).isUserAlreadyUpvoted((UUID) any(), (UUID) any());
        verify(this.voteRepository).getVoteIdByUserAndBlog((UUID) any(), (UUID) any());
        verify(this.voteRepository).deleteById((UUID) any());
        verify(this.userRepository).getById((UUID) any());
        verify(this.blogRepository).getById((UUID) any());
        verify(this.blogRepository).save((Blog) any());
    }

    @Test
    void testUpVoteClick2() {
        VoteObjects voteObjects = new VoteObjects();
        User user = voteObjects.getUserObjects();
        Blog blog = voteObjects.getBlogObjects(user);

        User user1 = voteObjects.getUserObjects();

        Vote vote = new Vote();
        vote.setBlog(blog);
        vote.setUser(user1);
        vote.setVoteId(UUID.randomUUID());
        when(this.voteRepository.getVoteIdByUserAndBlog((UUID) any(), (UUID) any())).thenReturn(UUID.randomUUID());
        doNothing().when(this.voteRepository).deleteById((UUID) any());
        when(this.voteRepository.isUserAlreadyUpvoted((UUID) any(), (UUID) any())).thenReturn(0);
        when(this.voteRepository.save((Vote) any())).thenReturn(vote);

        User user2 = voteObjects.getUserObjects();
        when(this.userRepository.getById((UUID) any())).thenReturn(user2);

        User user3 = voteObjects.getUserObjects();

        Blog blog1 = voteObjects.getBlogObjects(user3);

        User user4 = voteObjects.getUserObjects();

        Blog blog2 = voteObjects.getBlogObjects(user4);
        when(this.blogRepository.save((Blog) any())).thenReturn(blog2);
        when(this.blogRepository.getById((UUID) any())).thenReturn(blog1);
        UUID blogUUID = UUID.randomUUID();
        assertEquals("Upvoted successfully", this.voteServiceImpl.upVoteClick(blogUUID, UUID.randomUUID()));
        verify(this.voteRepository).isUserAlreadyUpvoted((UUID) any(), (UUID) any());
        verify(this.voteRepository).save((Vote) any());
        verify(this.userRepository).getById((UUID) any());
        verify(this.blogRepository).getById((UUID) any());
        verify(this.blogRepository).save((Blog) any());
    }
}

