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

@Entity
@Table(name = "Blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "blogUUID", updatable = false, nullable = false,  columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID blogUUID;

    @OneToOne(fetch = FetchType.EAGER,  cascade = CascadeType.MERGE)
    @JoinColumn(name  = "user_id", referencedColumnName = "user_id")
    private User user;
    @Nullable
    @Lob
    private String article;
    @NotBlank(message = "Blog name cannot be empty or null.")
    private String blogName;
    private Integer voteCount;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.EAGER,  cascade = CascadeType.MERGE)
    @JoinColumn(name = "voteUUID", referencedColumnName = "voteUUID")
    private Vote vote;

    public Blog() {
    }

    public Blog(UUID blogUUID, User user, String article) {
        this.blogUUID = blogUUID;
        this.user = user;
        this.article = article;
    }

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

    public String getBlogName(){return blogName;}

    public void setBlogName(String blogName){this.blogName = blogName;}

    public Instant getCreatedDate(){return createdDate;}

    public void setCreatedDate(Instant createdDate){this.createdDate = createdDate;}

    public Integer getVoteCount(){return voteCount;}

    public void setVoteCount(Integer voteCount){this.voteCount = voteCount;}
}
