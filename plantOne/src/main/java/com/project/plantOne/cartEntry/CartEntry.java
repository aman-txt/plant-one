package com.project.plantOne.cartEntry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.plantOne.cart.Cart;
import com.project.plantOne.post.Post;
import com.project.plantOne.user.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="CartEntry")
public class CartEntry {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "cart_entry_uuid", updatable = false, nullable = false,  columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID cartEntryUUID;
    @ManyToOne(fetch = FetchType.EAGER,  cascade = CascadeType.MERGE)
    @JoinColumn(name = "cart_uuid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cart cart;
    @OneToOne(fetch = FetchType.EAGER,  cascade = CascadeType.MERGE)
    @JoinColumn(name = "post_uuid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Post post;
    boolean isCheckedOut;
    boolean softDelete;

    public CartEntry() {
    }

    public CartEntry(UUID cartEntryUUID, Cart cart, Post post, boolean isCheckedOut, boolean softDelete, User user) {
        this.cartEntryUUID = cartEntryUUID;
        this.cart = cart;
        this.post = post;
        this.isCheckedOut = isCheckedOut;
        this.softDelete = softDelete;

    }

    public UUID getCartEntryUUID() {
        return cartEntryUUID;
    }

    public void setCartEntryUUID(UUID cartEntryUUID) {
        this.cartEntryUUID = cartEntryUUID;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }

    public boolean isSoftDelete() {
        return softDelete;
    }

    public void setSoftDelete(boolean softDelete) {
        this.softDelete = softDelete;
    }

}
