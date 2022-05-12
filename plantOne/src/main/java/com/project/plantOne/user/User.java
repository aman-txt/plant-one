package com.project.plantOne.user;

import org.hibernate.annotations.Type;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID user_id;
    private String first_name;
    private String last_name;
    @Column(unique = true)
    private String username;
    private String password;
    private boolean activeUser;
    private String user_role;
    @Email
    @Column(name="email",updatable = false,nullable = false,unique = true)
    @Size(min=1)
    private String email;
    private String street;
    private String city;
    private String country;
    private String postal_code;
    @Temporal(TemporalType.DATE)
    private Date dob;

    @Lob
    @Column(name="profile_picture")
    private byte[] profilePicture;

    private String fileExtension;

    public User() {
    }

    public User(UUID user_id, String first_name, String last_name, String username, String password, boolean activeUser, String user_role, String email, String street, String city, String country, String postal_code, Date dob) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.activeUser = activeUser;
        this.user_role = user_role;
        this.email = email;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postal_code = postal_code;
        this.dob = dob;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public boolean isActiveUser() {
        return activeUser;
    }

    public void setActiveUser(boolean activeUser) {
        this.activeUser = activeUser;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}
