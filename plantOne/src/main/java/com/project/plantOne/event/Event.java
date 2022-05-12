package com.project.plantOne.event;

import com.project.plantOne.user.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name  = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "event_uuid", updatable = false, nullable = false,  columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID eventUUID;
    @OneToOne(fetch = FetchType.EAGER,  cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false)
    private String title;
    @Lob
    @Column
    private String details;
    private String link;
    private Integer maximum_seats;
    private Integer available_seats;
    private String mode;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;


    public Event() {
    }

    public Event(UUID eventUUID, User user, String title, String details, String link, Integer maximum_seats, Integer available_seats, String mode, Date date) {
     this.eventUUID = eventUUID;
     this.user = user;
     this.title = title;
     this.details = details;
     this.link = link;
     this.maximum_seats = maximum_seats;
     this.available_seats = available_seats;
     this.mode = mode;
     this.date = date;
    }


     public UUID getEventUUID() {
      return eventUUID;
     }

     public void setEventUUID(UUID eventUUID) {
      this.eventUUID = eventUUID;
     }

     public User getUser() {
      return user;
     }

     public void setUser(User user) {
      this.user = user;
     }

     public String getTitle() {
      return title;
     }

     public void setTitle(String title) {
      this.title = title;
     }

     public String getDetails() {
      return details;
     }

     public void setDetails(String details) {
      this.details = details;
     }

     public String getLink() {
      return link;
     }

     public void setLink(String link) {
      this.link = link;
     }

     public Integer getMaximum_seats() {
      return maximum_seats;
     }

     public void setMaximum_seats(Integer maximum_seats) {
      this.maximum_seats = maximum_seats;
     }

     public Integer getAvailable_seats() {
      return available_seats;
     }

     public void setAvailable_seats(Integer available_seats) {
      this.available_seats = available_seats;
     }

     public String getMode() {
      return mode;
     }

     public void setMode(String mode) {
      this.mode = mode;
     }

     public Date getDate() {
      return date;
     }

     public void setDate(Date date) {
      this.date = date;
     }
}
