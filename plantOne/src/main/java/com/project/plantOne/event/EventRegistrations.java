package com.project.plantOne.event;

import com.project.plantOne.user.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "event_registration")
public class EventRegistrations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "event_registration_uuid", updatable = false, nullable = false,  columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID eventRegistrationUUID;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "event_uuid")
    private Event event;
    @OneToOne(fetch = FetchType.LAZY,  cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;
    String registrationId;

    public EventRegistrations() {

    }

    public EventRegistrations(UUID eventRegistrationUUID, Event event, User user, String registrationId) {
        this.eventRegistrationUUID = eventRegistrationUUID;
        this.event = event;
        this.user = user;
        this.registrationId = registrationId;
    }

    public UUID getEventRegistrationUUID() {
        return eventRegistrationUUID;
    }

    public void setEventRegistrationUUID(UUID eventRegistrationUUID) {
        this.eventRegistrationUUID = eventRegistrationUUID;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
}
