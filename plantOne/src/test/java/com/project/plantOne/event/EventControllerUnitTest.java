package com.project.plantOne.event;

import com.fasterxml.jackson.databind.ObjectMapper;

import static com.project.plantOne.constants.Constants.EVENTS_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@ExtendWith(SpringExtension.class)
@WebMvcTest(EventController.class)
@AutoConfigureMockMvc(addFilters = false)
@ComponentScan(basePackages = "com.project.plantOne.event")
public class EventControllerUnitTest {

    @Configuration
    @EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
    static class ContextConfiguration { }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventServiceImpl eventService;

    @Test
    public void getEventsTest() throws Exception {


        String uri = EVENTS_URL + "/";
        List<Event> eventList = new ArrayList<>();

        EventObjects eventObjects = new EventObjects();
        Event event = eventObjects.getEventObjects();

        eventList.add(event);
        Mockito.when(eventService.getEvents()).thenReturn(eventList);

        mockMvc.perform(get(uri).content(objectMapper.writeValueAsString(eventList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title").value(eventList.get(0).getTitle()));
    }

    @Test
    public void getEvent() throws Exception{

        String uri = EVENTS_URL + "/{event_id}";

        EventObjects eventObjects = new EventObjects();
        Event event = eventObjects.getEventObjects();

        Mockito.when(eventService.getEvent(any(UUID.class))).thenReturn(event);

        mockMvc.perform(get(uri,event.getEventUUID()).content(objectMapper.writeValueAsString(event))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void addEventTest() throws Exception {

        String uri = EVENTS_URL + "/add";

        EventObjects eventObjects = new EventObjects();
        Event event = eventObjects.getEventObjects();

        Mockito.when(eventService.addEvent(any(Event.class))).thenReturn(event);

        mockMvc.perform(post(uri).content(objectMapper.writeValueAsString(event))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void registerEventTest() throws Exception{


        String uri = EVENTS_URL + "/register/{event_id}";

        EventObjects eventObjects = new EventObjects();
        Event event = eventObjects.getEventObjects();
        event.setEventUUID(UUID.randomUUID());
        EventRegistrations eventRegistrations = eventObjects.getEventRegistrations();

        Mockito.when(eventService.registerEvent(eventRegistrations)).thenReturn("Your registration is successful.");

        mockMvc.perform(post(uri,event.getEventUUID()).content(objectMapper.writeValueAsString(eventRegistrations))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }




}
