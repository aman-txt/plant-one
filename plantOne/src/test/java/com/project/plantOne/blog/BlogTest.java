package com.project.plantOne.blog;

import com.project.plantOne.user.User;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class BlogTest {

    private static Integer expectedVotecount = 12;
    @Test
    void constructorTest() {

        BlogObjects blogObjects = new BlogObjects();
        User user = blogObjects.getUserObjects();

        UUID randomUUIDResult = UUID.randomUUID();

        Blog actualBlog = new Blog(randomUUIDResult,user,"Sample Test Article");
        actualBlog.setBlogName("Sample Test Name");
        actualBlog.setVoteCount(expectedVotecount);
        //        Blog actualBlog = blogObjects.getBlogObjects(user);
        actualBlog.setBlogUUID(randomUUIDResult);

        assertEquals("Sample Test Article", actualBlog.getArticle());
        assertEquals("Sample Test Name", actualBlog.getBlogName());
        assertSame(randomUUIDResult, actualBlog.getBlogUUID());
        assertSame(user, actualBlog.getUser());
        assertEquals(expectedVotecount, actualBlog.getVoteCount().intValue());
    }
}

