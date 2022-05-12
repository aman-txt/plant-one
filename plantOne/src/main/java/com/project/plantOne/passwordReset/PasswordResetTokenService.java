package com.project.plantOne.passwordReset;

import com.project.plantOne.user.User;

import java.util.Date;
import java.util.Optional;

public interface PasswordResetTokenService {

    void createPasswordResetTokenForUser(User user, String token);

    PasswordResetToken getPasswordResetToken(String token);

    public User findUserByEmail(final String email);

    public void changeUserPassword(final User user, PasswordDto passwordDto);

    public Optional<User> getUserByPasswordResetToken(final String token);

    public String validatePasswordResetToken(String token);

    public void deletePasswordResetToken(Date now);
}
