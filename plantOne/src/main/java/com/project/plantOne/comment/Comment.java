package com.project.plantOne.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.plantOne.post.Post;
import com.project.plantOne.user.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "comment_uuid", updatable = false, nullable = false,  columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID commentUUID;
    @Lob
    @Column()
    private String comment;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "post_uuid")
    private Post post;


    public Comment() {
    }

    public Comment(UUID commentUUID, String comment, User user, Post post) {
        this.commentUUID = commentUUID;
        this.comment = comment;
        this.user = user;
        this.post = post;
    }

    public UUID getCommentUUID() {
        return commentUUID;
    }

    public void setCommentUUID(UUID commentUUID) {
        this.commentUUID = commentUUID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
