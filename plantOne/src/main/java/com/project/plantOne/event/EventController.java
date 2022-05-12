package com.project.plantOne.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.project.plantOne.constants.Constants.EVENTS_URL;

@RestController
@RequestMapping(path = EVENTS_URL)
public class EventController {

    @Autowired
    private EventServiceImpl eventService;

    @GetMapping(value="/")
    public List<Event> getEvents(){

        return eventService.getEvents();
    }

    @GetMapping(value="/{event_id}")
    @ResponseBody
    public Event getEvent(@PathVariable("event_id") UUID eventUUID){
        return eventService.getEvent(eventUUID);
    }

    @PostMapping(value="/add")
    public Event addEvent(@RequestBody Event event){

        return eventService.addEvent(event);
    }

    @PostMapping(value = "/register/{event_id}")
    public String registerEvent(@RequestBody EventRegistrations eventRegistration){

        return eventService.registerEvent(eventRegistration);
    }

    @DeleteMapping(value="/{event_id}")
    public String deleteEventById(@PathVariable("event_id") UUID event_id){
        return eventService.deleteEventById(event_id);
    }


}




