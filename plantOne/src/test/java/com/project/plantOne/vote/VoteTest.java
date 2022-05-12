package com.project.plantOne.vote;

import com.project.plantOne.blog.Blog;
import com.project.plantOne.user.User;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;

class VoteTest {
    @Test
    void constructorTest() {
        VoteObjects voteObjects = new VoteObjects();
        Vote actualVote = new Vote();

        User user = voteObjects.getUserObjects();
        Blog blog = voteObjects.getBlogObjects(user);
        actualVote.setBlog(blog);

        User user1 = voteObjects.getUserObjects();
        actualVote.setUser(user1);

        UUID randomUUIDResult = UUID.randomUUID();
        actualVote.setVoteId(randomUUIDResult);
        assertSame(blog, actualVote.getBlog());
        assertSame(user1, actualVote.getUser());
        assertSame(randomUUIDResult, actualVote.getVoteId());
    }

    @Test
    void constructorTest2() {
        VoteObjects voteObjects = new VoteObjects();

        User user = voteObjects.getUserObjects();
        Blog blog = voteObjects.getBlogObjects(user);

        User user1 = voteObjects.getUserObjects();
        Blog blog1 = voteObjects.getBlogObjects(user1);
        Vote actualVote = new Vote(blog, user1);

        User user2 = voteObjects.getUserObjects();

        actualVote.setBlog(blog1);
        User user3 = voteObjects.getUserObjects();
        actualVote.setUser(user3);

        UUID randomUUIDResult = UUID.randomUUID();
        actualVote.setVoteId(randomUUIDResult);
        assertSame(blog1, actualVote.getBlog());
        assertSame(user3, actualVote.getUser());
        assertSame(randomUUIDResult, actualVote.getVoteId());
    }

    @Test
    void constructorTest3() {

        VoteObjects voteObjects = new VoteObjects();
        Vote actualVote = new Vote(UUID.randomUUID());
        User user = voteObjects.getUserObjects();
        Blog blog = voteObjects.getBlogObjects(user);
        actualVote.setBlog(blog);

        User user1 = voteObjects.getUserObjects();
        actualVote.setUser(user1);

        UUID randomUUIDResult = UUID.randomUUID();
        actualVote.setVoteId(randomUUIDResult);
        assertSame(blog, actualVote.getBlog());
        assertSame(user1, actualVote.getUser());
        assertSame(randomUUIDResult, actualVote.getVoteId());
    }
}

