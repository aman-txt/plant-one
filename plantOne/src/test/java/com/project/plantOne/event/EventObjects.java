package com.project.plantOne.event;

import com.project.plantOne.user.User;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class EventObjects {

    static final int availableSeats = 50;
    static int randomStringLength = 4;

    public User getUserObjects(){

        User user = new User();
        user.setUser_id(UUID.randomUUID());
        user.setActiveUser(true);
        user.setFirst_name("Test");
        user.setLast_name("Test");
        user.setCity("Test City");
        String testEmail = "test" + getRandomString() + "@dal.ca";
        user.setEmail(testEmail);
        user.setUser_role("TestRole");

        return user;

    }

    public Event getEventObjects() {

        User user = getUserObjects();

        Event event = new Event();

        event.setEventUUID(UUID.randomUUID());
        event.setAvailable_seats(availableSeats);
        event.setDate(new Date());
        event.setTitle("Test");
        event.setDetails("Test");
        event.setLink("Test.com");


        return event;

    }

    public EventRegistrations getEventRegistrations() {

        EventRegistrations eventRegistrations = new EventRegistrations();

        eventRegistrations.setEventRegistrationUUID(UUID.randomUUID());
        eventRegistrations.setEvent(getEventObjects());
        eventRegistrations.setUser(getUserObjects());

        return eventRegistrations;

    }

    public String getRandomString(){

        // create a string of all characters
        String string = "12345676890abcdefg";

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = randomStringLength;

        for(int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(string.length());

            // get character specified by index
            // from the string
            char randomChar = string.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

        return sb.toString();
    }

}
