package com.project.plantOne.cart;

import com.project.plantOne.user.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "cart_uuid", updatable = false, nullable = false,  columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID cartUUID;
    @OneToOne(fetch = FetchType.EAGER,  cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;


    public Cart() {
    }

    public Cart(UUID cartUUID, User user) {
        this.cartUUID = cartUUID;
        this.user = user;
    }

    public UUID getCartUUID() {
        return cartUUID;
    }

    public void setCartUUID(UUID cartUUID) {
        this.cartUUID = cartUUID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
