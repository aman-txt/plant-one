package com.project.plantOne.comment;

import com.project.plantOne.post.Post;
import com.project.plantOne.post.PostObjects;
import com.project.plantOne.user.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class CommentTest {
    @Test
    void constructorTest() {
        PostObjects postObjects = new PostObjects();
        Comment actualComment = new Comment();
        actualComment.setComment("Comment");
        UUID randomUUIDResult = UUID.randomUUID();
        actualComment.setCommentUUID(randomUUIDResult);
        User user = postObjects.getSellerUserObjects();

        User user1 = postObjects.getSellerUserObjects();
        Post post = postObjects.getPostObjects();
        post.setBuyer(user);
        post.setComment(new ArrayList<>());
        post.setSeller(user1);

        actualComment.setPost(post);
        User user2 = postObjects.getSellerUserObjects();
        actualComment.setUser(user2);
        assertEquals("Comment", actualComment.getComment());
        assertSame(randomUUIDResult, actualComment.getCommentUUID());
        assertSame(post, actualComment.getPost());
        assertSame(user2, actualComment.getUser());
    }
}

