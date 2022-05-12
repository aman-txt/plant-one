package com.project.plantOne.post;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "PostType")
public class PostType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "post_type_id", updatable = false, nullable = false,  columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID postTypeId;
    @Column(nullable = false)
    private String postType;

    public PostType() {
    }

    public PostType(UUID postTypeId, String postType) {
        this.postTypeId = postTypeId;
        this.postType = postType;
    }

    public UUID getPostTypeId() {
        return postTypeId;
    }

    public void setPostTypeId(UUID postTypeId) {
        this.postTypeId = postTypeId;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }
}
