package com.project.plantOne.blog;

import com.project.plantOne.exception.GlobalException;
import com.project.plantOne.user.User;
import com.project.plantOne.vote.VoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BlogServiceImpl.class})
@ExtendWith(SpringExtension.class)
class BlogServiceImplTest {
    @MockBean
    private BlogRepository blogRepository;

    @Autowired
    private BlogServiceImpl blogServiceImpl;

    @MockBean
    private VoteRepository voteRepository;


    @Test
    void getBlogTest() {

        BlogObjects blogObjects = new BlogObjects();
        User user = blogObjects.getUserObjects();
        Blog blog = blogObjects.getBlogObjects(user);

        Optional<Blog> ofResult = Optional.of(blog);
        when(this.blogRepository.findById((UUID) any())).thenReturn(ofResult);
        assertSame(blog, this.blogServiceImpl.getBlog(UUID.randomUUID()));
        verify(this.blogRepository).findById((UUID) any());
    }

    @Test
    void getBlogTest2() {
        when(this.blogRepository.findById((UUID) any())).thenThrow(new GlobalException("An error occurred", true));
        assertThrows(GlobalException.class, () -> this.blogServiceImpl.getBlog(UUID.randomUUID()));
        verify(this.blogRepository).findById((UUID) any());
    }

    @Test
    void addBlogTest() {
        BlogObjects blogObjects = new BlogObjects();
        Blog blog = blogObjects.getBlogObjects(blogObjects.getUserObjects());
        when(this.blogRepository.save((Blog) any())).thenReturn(blog);

        Blog blog1 = blogObjects.getBlogObjects(blogObjects.getUserObjects());
        Blog actualAddBlogResult = this.blogServiceImpl.addBlog(blog1);
        assertSame(blog1, actualAddBlogResult);
        assertEquals(0, actualAddBlogResult.getVoteCount().intValue());
        verify(this.blogRepository).save((Blog) any());
    }

    @Test
    void addBlogTest2() {
        BlogObjects blogObjects = new BlogObjects();
        Blog blog = blogObjects.getBlogObjects(blogObjects.getUserObjects());
        when(this.blogRepository.save((Blog) any())).thenReturn(blog);

        Blog blog1 = blogObjects.getBlogObjects(blogObjects.getUserObjects());
        Blog actualAddBlogResult = this.blogServiceImpl.addBlog(blog1);
        assertSame(blog1, actualAddBlogResult);
        assertEquals(0, actualAddBlogResult.getVoteCount().intValue());
        verify(this.blogRepository).save((Blog) any());

        when(this.blogRepository.findById((UUID) any())).thenThrow(new GlobalException("An error occurred", true));
        assertThrows(GlobalException.class, () -> this.blogServiceImpl.getBlog(UUID.randomUUID()));
        verify(this.blogRepository).findById((UUID) any());
    }

    @Test
    void getBlogsByUserTest() {
        when(this.blogRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.blogServiceImpl.getBlogsByUser(UUID.randomUUID()).isEmpty());
        verify(this.blogRepository).findAll();
    }

    @Test
    void getBlogsByUserTest2() {

        BlogObjects blogObjects = new BlogObjects();
        when(this.voteRepository.isUserAlreadyUpvoted((UUID) any(), (UUID) any())).thenReturn(1);

        Blog blog = blogObjects.getBlogObjects(blogObjects.getUserObjects());

        ArrayList<Blog> blogList = new ArrayList<>();
        blogList.add(blog);
        when(this.blogRepository.findAll()).thenReturn(blogList);
        assertEquals(1, this.blogServiceImpl.getBlogsByUser(UUID.randomUUID()).size());
        verify(this.voteRepository).isUserAlreadyUpvoted((UUID) any(), (UUID) any());
        verify(this.blogRepository).findAll();
    }

    @Test
    void getBlogsByUserTest3() {
        when(this.voteRepository.isUserAlreadyUpvoted((UUID) any(), (UUID) any())).thenReturn(0);


        BlogObjects blogObjects = new BlogObjects();
        User user = blogObjects.getUserObjects();
        Blog blog = blogObjects.getBlogObjects(user);

        ArrayList<Blog> blogList = new ArrayList<>();
        blogList.add(blog);
        when(this.blogRepository.findAll()).thenReturn(blogList);
        assertEquals(1, this.blogServiceImpl.getBlogsByUser(UUID.randomUUID()).size());
        verify(this.voteRepository).isUserAlreadyUpvoted((UUID) any(), (UUID) any());
        verify(this.blogRepository).findAll();
    }

    @Test
    void getBlogsByUserTest4() {
        when(this.voteRepository.isUserAlreadyUpvoted((UUID) any(), (UUID) any()))
                .thenThrow(new GlobalException("An error occurred", true));


        BlogObjects blogObjects = new BlogObjects();
        User user = blogObjects.getUserObjects();
        Blog blog = blogObjects.getBlogObjects(user);

        ArrayList<Blog> blogList = new ArrayList<>();
        blogList.add(blog);
        when(this.blogRepository.findAll()).thenReturn(blogList);
        assertThrows(GlobalException.class, () -> this.blogServiceImpl.getBlogsByUser(UUID.randomUUID()));
        verify(this.voteRepository).isUserAlreadyUpvoted((UUID) any(), (UUID) any());
        verify(this.blogRepository).findAll();
    }

    @Test
    void deleteBlogByIdTest() throws EmptyResultDataAccessException {

        BlogObjects blogObjects = new BlogObjects();
        User user = blogObjects.getUserObjects();

        Blog blog = blogObjects.getBlogObjects(user);

        when(this.blogRepository.getById((UUID) any())).thenReturn(blog);
        doNothing().when(this.blogRepository).deleteById((UUID) any());
        assertSame(blog, this.blogServiceImpl.deleteBlogById(UUID.randomUUID()));
        verify(this.blogRepository).getById((UUID) any());
        verify(this.blogRepository).deleteById((UUID) any());
    }


    @Test
    void deleteBlogByIdTest2() {

        BlogObjects blogObjects = new BlogObjects();
        User user = blogObjects.getUserObjects();
        Blog blog = blogObjects.getBlogObjects(user);

        when(this. blogRepository.getById((UUID) any())).thenThrow(new GlobalException("An error occurred", true));
        assertThrows(GlobalException.class, () -> this.blogServiceImpl.deleteBlogById(blog.getBlogUUID()));
        verify(this.blogRepository).getById((UUID) any());
    }

    @Test
    void updateBlogTest() {

        BlogObjects blogObjects = new BlogObjects();
        User user = blogObjects.getUserObjects();
        Blog blog = blogObjects.getBlogObjects(user);

        Optional<Blog> ofResult = Optional.of(blog);
        when(this.blogRepository.save((Blog) any())).thenThrow(new GlobalException("An error occurred", true));
        when(this.blogRepository.findById((UUID) any())).thenReturn(ofResult);

        User user1 = blogObjects.getUserObjects();
        Blog blog1 = blogObjects.getBlogObjects(user1);

        assertThrows(GlobalException.class, () -> this.blogServiceImpl.updateBlog(blog1));
        verify(this.blogRepository).save((Blog) any());
        verify(this.blogRepository, atLeast(1)).findById((UUID) any());
    }

    @Test
    void updateBlogTest2() {

        BlogObjects blogObjects = new BlogObjects();
        User user = blogObjects.getUserObjects();
        Blog blog = blogObjects.getBlogObjects(user);

        when(this.blogRepository.findById((UUID) any())).thenThrow(new GlobalException("An error occurred", true));
        assertThrows(GlobalException.class, () -> this.blogServiceImpl.updateBlog(blog));
        verify(this.blogRepository).findById((UUID) any());
    }

}

