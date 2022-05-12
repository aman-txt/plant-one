package com.project.plantOne.passwordReset;

import javax.transaction.Transactional;
import com.project.plantOne.user.User;
import com.project.plantOne.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";

    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token);
    }

    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(final String token) {
        return Optional.ofNullable(passwordTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changeUserPassword(final User user, PasswordDto passwordDto) {
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);
        passwordTokenRepository.deleteByToken(passwordDto.getToken());
    }

    @Override
    public void deletePasswordResetToken(Date now){
        passwordTokenRepository.deleteByExpiryDateLessThan(now);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        String status = !isTokenFound(passToken) ? TOKEN_INVALID
                : isTokenExpired(passToken) ? TOKEN_EXPIRED
                : "";
        return getMessage(status);
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

    private String getMessage(String result){
        String message = "";
        if(result.equalsIgnoreCase(TOKEN_EXPIRED)){
            message = "Your registration token has expired. Please register again.";
        }else if(result.equalsIgnoreCase(TOKEN_INVALID)){
            message = "Invalid token.";
        }
        return message;
    }

}
