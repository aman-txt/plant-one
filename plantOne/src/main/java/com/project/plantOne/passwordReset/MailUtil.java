package com.project.plantOne.passwordReset;

import com.project.plantOne.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static com.project.plantOne.constants.Constants.FRONTEND_RESET_PASSWORD_ROUTE;

@Component
public class MailUtil {

    @Autowired
    private Environment env;

    public SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final User user) {
        final String url = contextPath + token;
        final String message = "Click here to reset your password...";
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    public SimpleMailMessage constructEmail(String subject, String body, User user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    public String getAppUrl(HttpServletRequest request) {
        return request.getHeader("origin") + FRONTEND_RESET_PASSWORD_ROUTE;
    }
}
