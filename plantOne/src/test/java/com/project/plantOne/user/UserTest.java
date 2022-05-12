package com.project.plantOne.user;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void testConstructor() throws UnsupportedEncodingException {
        User actualUser = new User();
        actualUser.setActiveUser(true);
        actualUser.setCity("city");
        actualUser.setCountry("country");
        LocalDateTime atStartOfDayResult = LocalDate.of(2022, 4, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        actualUser.setDob(fromResult);
        actualUser.setEmail("testIntegration@plantone.com");
        actualUser.setFileExtension("jpg");
        actualUser.setFirst_name("Testing");
        actualUser.setLast_name("PlantOne");
        actualUser.setPassword("password");
        actualUser.setPostal_code("postalCode");
        actualUser.setProfilePicture("AAAAAAAA".getBytes("UTF-8"));
        actualUser.setStreet("Street");
        UUID randomUUIDResult = UUID.randomUUID();
        actualUser.setUser_id(randomUUIDResult);
        actualUser.setUser_role("user");
        actualUser.setUsername(null);

        assertEquals("city", actualUser.getCity());
        assertEquals("country", actualUser.getCountry());
        assertSame(fromResult, actualUser.getDob());
        assertEquals("testIntegration@plantone.com", actualUser.getEmail());
        assertEquals("jpg", actualUser.getFileExtension());
        assertEquals("Testing", actualUser.getFirst_name());
        assertEquals("PlantOne", actualUser.getLast_name());
        assertEquals("password", actualUser.getPassword());
        assertEquals("postalCode", actualUser.getPostal_code());
        assertEquals("Street", actualUser.getStreet());
        assertSame(randomUUIDResult, actualUser.getUser_id());
        assertEquals("user", actualUser.getUser_role());
        assertEquals(null, actualUser.getUsername());
        assertTrue(actualUser.isActiveUser());
    }

}

