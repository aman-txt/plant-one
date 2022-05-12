package com.project.plantOne.event;

import java.util.List;
import java.util.UUID;

public interface EventService {

    public List<Event> getEvents();

    public Event getEvent(UUID eventUUID);

    public Event addEvent(Event event);

    public String registerEvent(EventRegistrations eventRegistrations);

    public boolean checkAvailableSeats(UUID eventUUID);

    public String deleteEventById(UUID event_id);
}
