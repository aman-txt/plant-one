package com.project.plantOne.blog;

import com.project.plantOne.util.CustomModelMapper;
import com.project.plantOne.vote.VoteRepository;
import com.project.plantOne.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BlogServiceImpl implements  BlogService{

        @Autowired
        private BlogRepository blogRepository;

        @Autowired
        private VoteRepository voteRepository;

        public Blog getBlog(UUID blogUUID)
        {
            Optional<Blog> blog = blogRepository.findById(blogUUID);
            return blog.get();
        }

        public Blog addBlog(Blog blog)
        {
            String result;
            try{
                blog.setVoteCount(0);
                blog.setCreatedDate(Instant.now());
                blogRepository.save(blog);
            }catch (Exception exception)
            {
                throw new GlobalException("Exception: "+exception, false);
            }
            return blog;
        }

        public List<BlogResponse> getBlogsByUser(UUID user_id) {
            List<Blog> blogList = blogRepository.findAll();
            List<BlogResponse> blogResponsesList = new ArrayList<>();
            for (Blog blog:blogList) {
                BlogResponse blogResponse = new BlogResponse();
                blogResponse = CustomModelMapper.blogResponseModelMapper(blog);

                Integer isLiked = voteRepository.isUserAlreadyUpvoted(blog.getBlogUUID(),user_id);
                if(isLiked == 1)
                {
                    blogResponse.setLikedByUser(true);
                }else
                {
                    blogResponse.setLikedByUser(false);
                }
                blogResponsesList.add(blogResponse);
            }
            return blogResponsesList;
        }

        public Blog deleteBlogById(UUID blogUUID) throws EmptyResultDataAccessException {
            Blog blog = new Blog();
            try{
                blog = blogRepository.getById(blogUUID);
                blogRepository.deleteById(blogUUID);
            }catch(Exception exception){
                throw new GlobalException("Exception: "+exception, false);
            }
            return blog;
        }

        public Blog updateBlog(Blog blog){
            Blog originalBlog = new Blog();
            if (blogRepository.findById(blog.getBlogUUID()).isPresent()) {
                originalBlog = blogRepository.findById(blog.getBlogUUID()).get();
                originalBlog.setBlogName(blog.getBlogName());
                originalBlog.setArticle(blog.getArticle());
                originalBlog.setCreatedDate(Instant.now());
                blogRepository.save(originalBlog);
            } else {
                throw new GlobalException("Blog id does not exists.",false);
            }
            return originalBlog;
        }
}