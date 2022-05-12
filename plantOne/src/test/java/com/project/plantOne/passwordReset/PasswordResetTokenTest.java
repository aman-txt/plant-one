package com.project.plantOne.passwordReset;

import com.project.plantOne.user.User;
import com.project.plantOne.user.UserObjects;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PasswordResetTokenTest {
    @Test
    void testConstructor() throws UnsupportedEncodingException {
        PasswordResetToken actualPasswordResetToken = new PasswordResetToken();
        String token = UUID.randomUUID().toString();
        LocalDateTime atStartOfDayResult = LocalDate.of(2022, 4, 5).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        actualPasswordResetToken.setExpiryDate(fromResult);
        actualPasswordResetToken.setToken(token);
        UserObjects userObjects = new UserObjects();
        User user = userObjects.getUser();
        actualPasswordResetToken.setUser(user);
        assertSame(fromResult, actualPasswordResetToken.getExpiryDate());
        assertNull(actualPasswordResetToken.getId());
        assertEquals(token, actualPasswordResetToken.getToken());
        assertSame(user, actualPasswordResetToken.getUser());
    }


}

