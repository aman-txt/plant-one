package com.project.plantOne.passwordReset;

import com.project.plantOne.user.User;
import com.project.plantOne.user.UserObjects;
import com.project.plantOne.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {PasswordResetTokenServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PasswordResetTokenServiceImplTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordResetTokenServiceImpl passwordResetTokenServiceImpl;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testCreatePasswordResetTokenForUser() throws UnsupportedEncodingException {
        UserObjects userObjects = new UserObjects();
        User user = userObjects.getUser();

        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2022, 4, 1).atStartOfDay();
        passwordResetToken.setExpiryDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        when(this.passwordResetTokenRepository.save((PasswordResetToken) any())).thenReturn(passwordResetToken);


        this.passwordResetTokenServiceImpl.createPasswordResetTokenForUser(user, token);
        verify(this.passwordResetTokenRepository).save((PasswordResetToken) any());
    }

    @Test
    void testGetPasswordResetToken() throws UnsupportedEncodingException {
        UserObjects userObjects = new UserObjects();
        User user = userObjects.getUser();

        String token = UUID.randomUUID().toString();

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2022, 4, 1).atStartOfDay();
        passwordResetToken.setExpiryDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        when(this.passwordResetTokenRepository.findByToken((String) any())).thenReturn(passwordResetToken);
        assertSame(passwordResetToken, this.passwordResetTokenServiceImpl.getPasswordResetToken(token));
        verify(this.passwordResetTokenRepository).findByToken((String) any());
    }

    @Test
    void testFindUserByEmail() throws UnsupportedEncodingException {
        UserObjects userObjects = new UserObjects();
        User user = userObjects.getUser();

        when(this.userRepository.findByEmail((String) any())).thenReturn(user);
        assertSame(user, this.passwordResetTokenServiceImpl.findUserByEmail(user.getEmail()));
        verify(this.userRepository).findByEmail((String) any());
    }

    @Test
    void testGetUserByPasswordResetToken() throws UnsupportedEncodingException {
        UserObjects userObjects = new UserObjects();
        User user = userObjects.getUser();

        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2022, 4, 1).atStartOfDay();
        passwordResetToken.setExpiryDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        when(this.passwordResetTokenRepository.findByToken((String) any())).thenReturn(passwordResetToken);
        assertTrue(this.passwordResetTokenServiceImpl.getUserByPasswordResetToken(token).isPresent());
        verify(this.passwordResetTokenRepository).findByToken((String) any());
    }

    @Test
    void testChangeUserPassword() throws UnsupportedEncodingException {
        UserObjects userObjects = new UserObjects();
        User user = userObjects.getUser();

        when(this.userRepository.save((User) any())).thenReturn(user);
        doNothing().when(this.passwordResetTokenRepository).deleteByToken((String) any());
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("password");

        User user1 = userObjects.getUser();
        String token = UUID.randomUUID().toString();
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setEmail(user.getEmail());
        passwordDto.setNewPassword("newPassword");
        passwordDto.setToken(token);
        this.passwordResetTokenServiceImpl.changeUserPassword(user1, passwordDto);
        verify(this.userRepository).save((User) any());
        verify(this.passwordResetTokenRepository).deleteByToken((String) any());
        verify(this.passwordEncoder).encode((CharSequence) any());
        assertEquals("password", user1.getPassword());
    }

    @Test
    void testDeletePasswordResetToken() {
        doNothing().when(this.passwordResetTokenRepository).deleteByExpiryDateLessThan((Date) any());
        LocalDateTime atStartOfDayResult = LocalDate.of(2022, 4, 1).atStartOfDay();
        this.passwordResetTokenServiceImpl
                .deletePasswordResetToken(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        verify(this.passwordResetTokenRepository).deleteByExpiryDateLessThan((Date) any());
    }

    @Test
    void testValidatePasswordResetToken() throws UnsupportedEncodingException {
        UserObjects userObjects = new UserObjects();
        User user = userObjects.getUser();
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2022, 4, 1).atStartOfDay();
        passwordResetToken.setExpiryDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        when(this.passwordResetTokenRepository.findByToken((String) any())).thenReturn(passwordResetToken);
        assertEquals("Your registration token has expired. Please register again.",
                this.passwordResetTokenServiceImpl.validatePasswordResetToken(token));
        verify(this.passwordResetTokenRepository).findByToken((String) any());
    }
}

