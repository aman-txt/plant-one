package com.project.plantOne.user;

import com.project.plantOne.post.PostController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest{

    @Autowired
    UserRepository userRepository;

    @MockBean
    PostController postController;

    UserObjects userObjects = new UserObjects();

    @Test
    void findByEmail() {
        User user = userObjects.getUser();
        userRepository.save(user);
        User userOutput = userRepository.findByEmail(user.getEmail());
        assertEquals(user.getEmail(),userOutput.getEmail());
        userRepository.delete(user);
    }

}