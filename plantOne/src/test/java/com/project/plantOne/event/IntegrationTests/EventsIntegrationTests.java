package com.project.plantOne.event.IntegrationTests;


import com.project.plantOne.AbstractTest;
import com.project.plantOne.event.EventObjects;
import com.project.plantOne.PlantOneApplication;


import com.project.plantOne.event.Event;
import com.project.plantOne.event.EventRegistrations;
import com.project.plantOne.event.EventRepository;
import com.project.plantOne.user.User;
import com.project.plantOne.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
;

import static com.project.plantOne.constants.Constants.EVENTS_URL;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PlantOneApplication.class)
@WebAppConfiguration
@EnableWebMvc
public class EventsIntegrationTests extends AbstractTest {

    /*
        Integration Testing - EventController.java
        Author - Sarthak Pandit <sr215260@dal.ca>
     */

    int expectedStatus = 200;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    EventObjects eventObjects = new EventObjects();
    User savedUser = new User();

    @Override
    @Before
    public void setUp() {

        User user  = eventObjects.getUserObjects();
        User savedUser = userRepository.save(user);
        super.setUp();
    }

    @Test
    public void getEventsTest() throws Exception {

        String uri = EVENTS_URL + "/";
        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, status);

    }

    @Test
    public void getEventTest() throws Exception{

        EventObjects eventObjects = new EventObjects();

        String uri = EVENTS_URL + "/{event_id}";
        Event event = eventObjects.getEventObjects();

        event.setUser(userRepository.findAll().get(0));
        eventRepository.save(event);
        Event savedEvent = eventRepository.findAll().get(0);

        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders.get(uri,savedEvent.getEventUUID())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, status);
    }

    @Test
    public void registerEventTest() throws Exception{

        String uri = EVENTS_URL + "/register/{event_id}";

        EventObjects eventObjects = new EventObjects();
        Event event = eventObjects.getEventObjects();
        event.setUser(userRepository.findAll().get(0));
        eventRepository.save(event);

        Event savedEvent = eventRepository.findAll(Sort.by(Sort.Direction.DESC,"date")).get(0);

        EventRegistrations eventRegistrations = new EventRegistrations();
        eventRegistrations.setEvent(savedEvent);
        eventRegistrations.setUser(savedEvent.getUser());



        String inputJson = super.mapToJson(eventRegistrations);
        MvcResult mvcResult =
                mvc.perform(MockMvcRequestBuilders.post(uri, savedEvent.getEventUUID())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatus, status);
    }

    @Test
    public void addEventTest() throws Exception{

        String uri = EVENTS_URL + "/add";
        EventObjects eventObjects = new EventObjects();
        Event event = eventObjects.getEventObjects();
        event.setUser(userRepository.findAll().get(0));



        String inputJson = super.mapToJson(event);
        MvcResult mvcResult =
                mvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertEquals(expectedStatus, status);


    }



}
