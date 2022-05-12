package com.project.plantOne.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRegistrationRepository extends JpaRepository<EventRegistrations, UUID> {


}
