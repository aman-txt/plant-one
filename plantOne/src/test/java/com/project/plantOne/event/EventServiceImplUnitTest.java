package com.project.plantOne.event;

import com.project.plantOne.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class EventServiceImplUnitTest {


    @Mock
    EventRepository eventRepository;

    @Mock
    EventRegistrationRepository eventRegistrationRepository;

    @InjectMocks
    EventServiceImpl eventService;

    @Mock
    UserRepository userRepository;


    @Test
    public void getEventsTest() {

        EventObjects eventObjects = new EventObjects();
        List<Event> eventList = new ArrayList<>();

        Event event = eventObjects.getEventObjects();
//        event.setUser(userRepository.findAll().get(1));
        eventRepository.save(event);

        eventRepository.save(event);
        eventList.add(event);

        Mockito.when(eventRepository.findAll()).thenReturn(eventList);
        List<Event> responseEventList = eventService.getEvents();
        assertThat(responseEventList.get(0).getTitle()).isSameAs(eventList.get(0).getTitle());

    }

    @Test
    public void getEventTest(){

        EventObjects eventObjects = new EventObjects();

        Event event = eventObjects.getEventObjects();
        eventRepository.save(event);

        Mockito.when(eventRepository.findById(event.getEventUUID())).thenReturn(Optional.of(event));
        Event responseEventList = eventService.getEvent(event.getEventUUID());
        assertThat(responseEventList.getTitle()).isSameAs(event.getTitle());

    }

    @Test
    public void addEventTest(){

        EventObjects eventObjects = new EventObjects();

        Event event = eventObjects.getEventObjects();
        eventRepository.save(event);

        Mockito.when(eventRepository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);
        Event responseEvent = eventService.addEvent(event);
        assertThat(responseEvent.getAvailable_seats()).isSameAs(responseEvent.getMaximum_seats());
        assertThat(responseEvent.getDetails()).isSameAs(event.getDetails());

    }

    @Test
    public void registerEventTest(){

        EventServiceImpl mockEventService = Mockito.spy(new EventServiceImpl());

        EventObjects eventObjects = new EventObjects();
        Event event = eventObjects.getEventObjects();

        EventRegistrationRepository eventRegistrationRepository = Mockito.mock(EventRegistrationRepository.class);

        eventRepository.save(event);
        EventRegistrations eventRegistrations = eventObjects.getEventRegistrations();

        Mockito.doReturn(false).when(mockEventService).checkAvailableSeats(Mockito.any(UUID.class));
        String expectedOutput ="Sorry, all seats have been booked.";

        String actualOutput = mockEventService.registerEvent(eventRegistrations);
        assertEquals(actualOutput, expectedOutput);


    }




}
