package com.project.plantOne.like;

import com.project.plantOne.post.Post;
import com.project.plantOne.user.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "Likes")
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "likeUUID", updatable = false, nullable = false,  columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID likeUUID;

    public Likes()
    {

    }
    public Likes(UUID likeUUID) {
        this.likeUUID = likeUUID;
    }

    @NotNull
    @ManyToOne(fetch = EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "post_uuid")
    private Post post;

    @ManyToOne(fetch = EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    public Likes(Post post, User user) {
        this.post = post;
        this.user = user;
    }

    public UUID getLikeId() {
        return likeUUID;
    }

    public void setLikeId(UUID likeUUID) {
        this.likeUUID = likeUUID;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}