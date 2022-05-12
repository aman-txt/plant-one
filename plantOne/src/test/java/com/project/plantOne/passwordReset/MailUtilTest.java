package com.project.plantOne.passwordReset;

import com.project.plantOne.user.User;
import com.project.plantOne.user.UserObjects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MailUtil.class})
@ExtendWith(SpringExtension.class)
class MailUtilTest {
    @MockBean
    private Environment environment;

    @Autowired
    private MailUtil mailUtil;

    @Test
    void testConstructResetTokenEmail() throws UnsupportedEncodingException {
        when(this.environment.getProperty((String) any())).thenReturn("Property");
        Locale locale = new Locale("en");

        UserObjects userObjects = new UserObjects();
        User user = userObjects.getUser();

        SimpleMailMessage actualConstructResetTokenEmailResult = this.mailUtil.constructResetTokenEmail("Context Path",
                locale, "ABC123", user);
        assertEquals(1, actualConstructResetTokenEmailResult.getTo().length);
        assertEquals("Click here to reset your password... \r\nContext PathABC123",
                actualConstructResetTokenEmailResult.getText());
        assertEquals("Reset Password", actualConstructResetTokenEmailResult.getSubject());
        assertEquals("Property", actualConstructResetTokenEmailResult.getFrom());
        verify(this.environment).getProperty((String) any());
    }

    @Test
    void testConstructEmail() throws UnsupportedEncodingException {
        when(this.environment.getProperty((String) any())).thenReturn("Property");

        UserObjects userObjects = new UserObjects();
        User user = userObjects.getUser();
        SimpleMailMessage actualConstructEmailResult = this.mailUtil.constructEmail("Testing subject",
                "Testing text", user);
        assertEquals(1, actualConstructEmailResult.getTo().length);
        assertEquals("Testing text", actualConstructEmailResult.getText());
        assertEquals("Testing subject", actualConstructEmailResult.getSubject());
        assertEquals("Property", actualConstructEmailResult.getFrom());
        verify(this.environment).getProperty((String) any());
    }

    @Test
    void testGetAppUrl() {
        assertEquals("null/#/verify-change-password/", this.mailUtil.getAppUrl(new MockHttpServletRequest()));
    }
}

