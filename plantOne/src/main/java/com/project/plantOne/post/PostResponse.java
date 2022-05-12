package com.project.plantOne.post;

import com.project.plantOne.comment.Comment;
import com.project.plantOne.like.Likes;
import com.project.plantOne.user.User;

import javax.persistence.Column;
import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PostResponse {
    private UUID postUUID;
    private String title;
    private User seller;
    private User buyer;
    private Date timestamp;
    private boolean is_archive;
    private double price;
    private PlantType plantType;
    private PostType postType;
    private List<Comment> comment;
    private Likes likes;
    private Integer likesCount;
    private Boolean isLikedByUser;
    private String base64Image;

    public UUID getPostUUID() {
        return postUUID;
    }

    public void setPostUUID(UUID postUUID) {
        this.postUUID = postUUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isIs_archive() {
        return is_archive;
    }

    public void setIs_archive(boolean is_archive) {
        this.is_archive = is_archive;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public void setPlantType(PlantType plantType) {
        this.plantType = plantType;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Boolean getLikedByUser() {
        return isLikedByUser;
    }

    public void setLikedByUser(Boolean likedByUser) {
        isLikedByUser = likedByUser;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}
