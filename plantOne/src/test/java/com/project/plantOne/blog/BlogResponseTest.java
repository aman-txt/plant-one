package com.project.plantOne.blog;

import com.project.plantOne.user.User;
import com.project.plantOne.vote.Vote;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BlogResponseTest {
    @Test
    void testConstructor() {
        BlogResponse actualBlogResponse = new BlogResponse();
        actualBlogResponse.setArticle("Article");
        actualBlogResponse.setBlogName("Blog Name");
        UUID randomUUIDResult = UUID.randomUUID();
        actualBlogResponse.setBlogUUID(randomUUIDResult);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        actualBlogResponse.setCreatedDate(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        actualBlogResponse.setLikedByUser(true);
        User user = new User();
        user.setActiveUser(true);
        user.setCity("Oxford");
        user.setCountry("GB");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setDob(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPostal_code("Postal code");
        user.setStreet("Street");
        user.setUser_id(UUID.randomUUID());
        user.setUser_role("User role");
        user.setUsername("janedoe");
        actualBlogResponse.setUser(user);
        User user1 = new User();
        user1.setActiveUser(true);
        user1.setCity("Oxford");
        user1.setCountry("GB");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setDob(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        user1.setEmail("jane.doe@example.org");
        user1.setFirst_name("Jane");
        user1.setLast_name("Doe");
        user1.setPassword("iloveyou");
        user1.setPostal_code("Postal code");
        user1.setStreet("Street");
        user1.setUser_id(UUID.randomUUID());
        user1.setUser_role("User role");
        user1.setUsername("janedoe");
        Blog blog = new Blog();
        blog.setArticle("Article");
        blog.setBlogName("Blog Name");
        blog.setBlogUUID(UUID.randomUUID());
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        blog.setCreatedDate(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant());
        blog.setUser(user1);
        blog.setVoteCount(3);
        User user2 = new User();
        user2.setActiveUser(true);
        user2.setCity("Oxford");
        user2.setCountry("GB");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setDob(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        user2.setEmail("jane.doe@example.org");
        user2.setFirst_name("Jane");
        user2.setLast_name("Doe");
        user2.setPassword("iloveyou");
        user2.setPostal_code("Postal code");
        user2.setStreet("Street");
        user2.setUser_id(UUID.randomUUID());
        user2.setUser_role("User role");
        user2.setUsername("janedoe");
        Vote vote = new Vote();
        vote.setBlog(blog);
        vote.setUser(user2);
        vote.setVoteId(UUID.randomUUID());
        actualBlogResponse.setVote(vote);
        actualBlogResponse.setVoteCount(3);
        assertEquals("Article", actualBlogResponse.getArticle());
        assertEquals("Blog Name", actualBlogResponse.getBlogName());
        assertSame(randomUUIDResult, actualBlogResponse.getBlogUUID());
        assertTrue(actualBlogResponse.getLikedByUser());
        assertSame(user, actualBlogResponse.getUser());
        assertSame(vote, actualBlogResponse.getVote());
        assertEquals(3, actualBlogResponse.getVoteCount().intValue());
    }
}

