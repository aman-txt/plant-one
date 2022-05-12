package com.project.plantOne.vote;

import com.project.plantOne.blog.Blog;
import com.project.plantOne.user.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "Vote")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "voteUUID", updatable = false, nullable = false,  columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID voteUUID;


    public Vote()
    {

    }
    public Vote(UUID voteUUID) {
        this.voteUUID = voteUUID;
    }

    @NotNull
    @ManyToOne(fetch = EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "blogUUID", referencedColumnName = "blogUUID")
    private Blog blog;

    @ManyToOne(fetch = EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    public Vote(Blog blog, User user) {
        this.blog = blog;
        this.user = user;
    }

    public UUID getVoteId() {
        return voteUUID;
    }

    public void setVoteId(UUID voteUUID) {
        this.voteUUID = voteUUID;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}