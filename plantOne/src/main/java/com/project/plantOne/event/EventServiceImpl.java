package com.project.plantOne.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.project.plantOne.constants.Constants.*;
import static java.util.UUID.randomUUID;

@Service
public class EventServiceImpl implements EventService{

        @Autowired
        private EventRepository eventRepository;

        @Autowired
        private EventRegistrationRepository eventRegistrationRepository;

        public List<Event> getEvents(){

           return eventRepository.findAll();
        }

        public Event getEvent(UUID eventUUID)
        {
            Optional<Event> event = eventRepository.findById(eventUUID);
            return event.get();
        }

        public Event addEvent(Event event)
        {
            event.setAvailable_seats(event.getMaximum_seats());
            return eventRepository.save(event);
        }

        public String registerEvent(EventRegistrations eventRegistrations){

            UUID eventUUID = eventRegistrations.getEvent().getEventUUID();
            if(checkAvailableSeats(eventUUID)) {
                String usernameRegistrationId = eventRegistrations.getUser().getUsername();
                String uuidRegistrationId = eventUUID.toString().substring(EVENT_UUID_BEGIN_INDEX,EVENT_UUID_END_INDEX)
                        + randomUUID().toString().substring(EVENT_UUID_BEGIN_INDEX,RANDOM_EVENT_UUID_END_INDEX);
                String registrationId = usernameRegistrationId + uuidRegistrationId;
                eventRegistrations.setRegistrationId(registrationId);
                eventRegistrationRepository.save(eventRegistrations);
                return "Your registration is successful.\nPlease note your registration id is: " + registrationId;
            }
            else
                return "Sorry, all seats have been booked.";

        }

        public boolean checkAvailableSeats(UUID eventUUID){

            Optional <Event> event = eventRepository.findById(eventUUID);
            int availableSeats = event.get().getAvailable_seats();
            if(availableSeats > MIN_AVAILABLE_SEATS){
                availableSeats--;
                event.get().setAvailable_seats(availableSeats);
                return true;
            }
            else
                return false;

        }

        public String deleteEventById(UUID event_id) throws EmptyResultDataAccessException {
            String result;
            try{
                eventRepository.deleteById(event_id);
                result = "Event deleted successfully";
            }catch(Exception exception){
                result = "Exception:"+exception;
            }
            return result;
        }
}


