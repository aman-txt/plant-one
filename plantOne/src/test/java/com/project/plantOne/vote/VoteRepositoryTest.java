package com.project.plantOne.vote;

import com.project.plantOne.blog.BlogRepository;
import com.project.plantOne.post.PostController;
import com.project.plantOne.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    PostController postController;

    VoteObjects voteObjects = new VoteObjects();
    UUID voteUUID;

//    @BeforeEach
//    public void createVote(){
//
//        User user = voteObjects.getUserObjects();
//        Blog blog = voteObjects.getBlogObjects(user);
//        Vote vote = voteObjects.getVoteObjects(user,blog);
//        voteUUID = UUID.randomUUID();
//        vote.setVoteId(voteUUID);
//        voteRepository.save(vote);
//
//    }
//    @Test
//    void testIsUserAlreadyUpvoted() {
//        VoteObjects voteObjects = new VoteObjects();
//        Vote vote = new Vote();
//
//        User user = voteObjects.getUserObjects();
//        Blog blog = voteObjects.getBlogObjects(user);
//        vote.setBlog(blog);
//
//        User user1 = voteObjects.getUserObjects();
//        vote.setUser(user1);
//
//        this.voteRepository.save(vote);
//
//        Vote vote1 = new Vote();
//
//        User user2 = voteObjects.getUserObjects();
//        Blog blog1 = voteObjects.getBlogObjects(user2);
//
//        vote1.setBlog(blog1);
//
//        User user3 = voteObjects.getUserObjects();
//        vote1.setUser(user3);
//        this.voteRepository.save(vote1);
//        UUID randomUUIDResult = UUID.randomUUID();
//        this.voteRepository.isUserAlreadyUpvoted(randomUUIDResult, UUID.randomUUID());
//    }
//
//    @Test
//    void testGetVoteIdByUserAndBlog() {
//        VoteObjects voteObjects = new VoteObjects();
//        Vote vote = new Vote();
//
//        User user = voteObjects.getUserObjects();
//        Blog blog = voteObjects.getBlogObjects(user);
//        vote.setBlog(blog);
//
//        User user1 = voteObjects.getUserObjects();
//        vote.setUser(user1);
//        this.voteRepository.save(vote);
//
//        Vote vote1 = new Vote();
//
//        User user2 = voteObjects.getUserObjects();
//        Blog blog1 = voteObjects.getBlogObjects(user2);
//        vote1.setBlog(blog1);
//
//        User user3 = voteObjects.getUserObjects();
//        vote1.setUser(user3);
//        this.voteRepository.save(vote1);
//        UUID randomUUIDResult = UUID.randomUUID();
//        this.voteRepository.getVoteIdByUserAndBlog(randomUUIDResult, UUID.randomUUID());
//    }
//
//    @Test
//    void isUserAlreadyUpvotedTest() {
//
//        User user = voteObjects.getUserObjects();
//        Blog blog = voteObjects.getBlogObjects(user);
//        voteUUID = UUID.randomUUID();
//        Vote vote = new Vote();
//        vote.setVoteId(voteUUID);
//        vote.setBlog(blog);
//        vote.setUser(user);
//
//
//        voteRepository.save(vote);
//
//        Integer voteResponse = voteRepository.isUserAlreadyUpvoted(vote.getBlog().getBlogUUID(),vote.getUser().getUser_id());
////        UUID voteResponse = voteRepository.getVoteIdByUserAndBlog(vote.getBlog().getBlogUUID(),vote.getUser().getUser_id());
//        assertEquals(voteResponse,1);
//    }
}

