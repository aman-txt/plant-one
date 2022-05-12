package com.project.plantOne.blog;

import com.project.plantOne.user.User;
import com.project.plantOne.vote.Vote;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;

public class BlogResponse {

    private UUID blogUUID;
    private User user;
    private String article;
    private String blogName;
    private Integer voteCount;
    private Instant createdDate;
    private Vote vote;
    private Boolean isLikedByUser;

    public UUID getBlogUUID() {
        return blogUUID;
    }

    public void setBlogUUID(UUID blogUUID) {
        this.blogUUID = blogUUID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public Boolean getLikedByUser() {
        return isLikedByUser;
    }

    public void setLikedByUser(Boolean likedByUser) {
        isLikedByUser = likedByUser;
    }
}
