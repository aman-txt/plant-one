package com.project.plantOne.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.plantOne.comment.Comment;
import com.project.plantOne.like.Likes;
import com.project.plantOne.user.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "post_uuid", updatable = false, nullable = false,  columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID postUUID;
    @Column(nullable = false)
    private String title;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "seller_uuid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User seller;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "buyer_uuid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User buyer;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    private boolean is_archive;
    private double price;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "plantTypeUUID")
    private PlantType plantType;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "postTypeUUID")
    private PostType postType;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "post_uuid")
    private List<Comment> comment;

    @ManyToOne
    @JoinColumn(name = "likeUUID")
    private Likes likes;

    private Integer likesCount;
    @Lob
    @Column(name="plant_image")
    private byte[] plantImage;

    private String fileExtension;
    private String base64Image;


    public Post() {
    }

    public Post(UUID postUUID, String title, User seller, User buyer, Date timestamp, boolean is_archive, double price, PlantType plantType, PostType postType, List<Comment> comment, Integer likesCount) {
        this.postUUID = postUUID;
        this.title = title;
        this.seller = seller;
        this.buyer = buyer;
        this.timestamp = timestamp;
        this.is_archive = is_archive;
        this.price = price;
        this.plantType = plantType;
        this.postType = postType;
        this.comment = comment;
        this.likesCount = likesCount;
    }

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

    public Integer getLikesCount(){ return likesCount;}

    public void setLikesCount(Integer likesCount){this.likesCount = likesCount;}

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public byte[] getPlantImage() {
        return plantImage;
    }

    public void setPlantImage(byte[] plantImage) {
        this.plantImage = plantImage;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}
