package com.project.plantOne.like;

import com.project.plantOne.blog.Blog;
import com.project.plantOne.blog.BlogResponse;
import com.project.plantOne.post.PostRepository;
import com.project.plantOne.user.User;
import com.project.plantOne.user.UserRepository;
import com.project.plantOne.vote.Vote;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class LikeObjects {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    private static String cityTest = "Halifax";
    private static String countryTest = "Canada";
    private static String testFNameTest = "NameTest";
    private static String testLNameTest = "SurnameTest";
    private static String passwordTest = "pwd123";
    private static String postalCodeTest = "B3J2K9";
    private static String streetTest = "South Park";
    private static String userRoleTest = "Admin";
    private static Integer voteCountTest = 12;
    private static String blogNameTest = "Sample Test Name";
    private static String blogArticleTest = "Sample Test Article";
    private static int lengthTest = 3;
    private static String allCharString = "123456767890abcdefg";

    public User getUserObjects(){

        User user = new User();
        user.setActiveUser(true);
        user.setCity(cityTest);
        user.setCountry(countryTest);
        user.setDob(Date.from(Instant.now()));
        byte[] ar = new byte[3];
        new Random().nextBytes(ar);
        String testEmail = "test" + getRandomString() + "@gmail.com";
        user.setEmail(testEmail);
        user.setFirst_name(testFNameTest);
        user.setLast_name(testLNameTest);
        user.setPassword(passwordTest);
        user.setPostal_code(postalCodeTest);
        user.setStreet(streetTest);
        user.setUser_id(UUID.randomUUID());
        user.setUser_role(userRoleTest);
        user.setUsername(testEmail);

        return user;

    }

    public String getRandomString(){

        // create a string of all characters
        String string = allCharString;

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = lengthTest;

        for(int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(string.length());

            // get character specified by index
            // from the string
            char randomChar = string.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

        return sb.toString();
    }


    public Blog getBlogObjects(User user)
    {
        Blog blog = new Blog();
        blog.setArticle(blogArticleTest);
        blog.setBlogName(blogNameTest);
        blog.setBlogUUID(UUID.randomUUID());
        blog.setCreatedDate(Instant.now());
        blog.setUser(user);
        blog.setVoteCount(voteCountTest);
        return blog;
    }

    public Vote getVoteObjects(User user, Blog blog)
    {
        Vote vote = new Vote();
        vote.setUser(user);
        vote.setBlog(blog);
        return vote;
    }


    public BlogResponse blogResponseModelMapper(Blog blog){
        ModelMapper modelMapper = new ModelMapper();
        BlogResponse blogResponse = modelMapper.map(blog, BlogResponse.class);
        return blogResponse;
    }

}
