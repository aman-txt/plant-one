package com.project.plantOne.like;

import com.project.plantOne.post.Post;
import com.project.plantOne.post.PostObjects;
import com.project.plantOne.user.User;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;

class LikesTest {
    @Test
    void constructorTest() {
        PostObjects postObjects = new PostObjects();
        Likes actualLikes = new Likes();
        UUID randomUUIDResult = UUID.randomUUID();
        actualLikes.setLikeId(randomUUIDResult);
        Post post = postObjects.getPostObjects();
        actualLikes.setPost(post);
        User user2 = postObjects.getBuyerUserObjects();
        actualLikes.setUser(user2);
        assertSame(randomUUIDResult, actualLikes.getLikeId());
        assertSame(post, actualLikes.getPost());
        assertSame(user2, actualLikes.getUser());
    }
}

