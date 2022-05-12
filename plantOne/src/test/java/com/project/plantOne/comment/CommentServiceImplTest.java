package com.project.plantOne.comment;

import com.project.plantOne.post.Post;
import com.project.plantOne.post.PostObjects;
import com.project.plantOne.post.PostRepository;
import com.project.plantOne.user.User;
import com.project.plantOne.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CommentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CommentServiceImplTest {
    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentServiceImpl commentServiceImpl;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testAddComment() {
        PostObjects postObjects = new PostObjects();
        User user = postObjects.getSellerUserObjects();
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById((UUID) any())).thenReturn(ofResult);

        User user1 = postObjects.getBuyerUserObjects();

        User user2 = postObjects.getBuyerUserObjects();

        Post post = postObjects.getPostObjects();
        post.setBuyer(user1);
        post.setSeller(user2);
        when(this.postRepository.findBypostUUID((UUID) any())).thenReturn(post);

        User user3 = postObjects.getSellerUserObjects();

        User user4 = postObjects.getSellerUserObjects();

        Post post1 = postObjects.getPostObjects();
        post1.setBuyer(user3);
        post1.setSeller(user4);

        User user5 = postObjects.getBuyerUserObjects();

        Comment comment = new Comment();
        comment.setComment("Comment");
        comment.setCommentUUID(UUID.randomUUID());
        comment.setPost(post1);
        comment.setUser(user5);
        when(this.commentRepository.save((Comment) any())).thenReturn(comment);
        UUID postUUID = UUID.randomUUID();

        User user6 = postObjects.getBuyerUserObjects();

        User user7 = postObjects.getBuyerUserObjects();

        Post post2 = postObjects.getPostObjects();
        post2.setBuyer(user6);
        post2.setSeller(user7);

        User user8 = postObjects.getBuyerUserObjects();

        Comment comment1 = new Comment();
        comment1.setComment("Comment");
        comment1.setCommentUUID(UUID.randomUUID());
        comment1.setPost(post2);
        comment1.setUser(user8);
        assertSame(comment, this.commentServiceImpl.addComment(postUUID, comment1));
        verify(this.userRepository).findById((UUID) any());
        verify(this.postRepository).findBypostUUID((UUID) any());
        verify(this.commentRepository).save((Comment) any());
    }
}

