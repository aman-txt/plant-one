package com.project.plantOne.passwordReset;

import com.project.plantOne.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

import static com.project.plantOne.constants.Constants.PASSWORD_RESET_HOME;

@RestController
@RequestMapping(path=PASSWORD_RESET_HOME)
public class PasswordResetTokenController {

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailUtil mailUtil;

    @PostMapping("/changePassword")
    public String savePassword(@RequestBody PasswordDto passwordDto) {
        Optional<User> user = passwordResetTokenService.getUserByPasswordResetToken(passwordDto.getToken());
        if(user.isPresent()) {
            passwordResetTokenService.changeUserPassword(user.get(), passwordDto);
            return "Password reset successfully";
        } else {
            return "Invalid";
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity resetPassword(final HttpServletRequest request, @RequestParam("email") final String email) {
        final User user = passwordResetTokenService.findUserByEmail(email);
        if (user != null) {
            final String token = UUID.randomUUID().toString();
            passwordResetTokenService.createPasswordResetTokenForUser(user, token);
            String appUrl = mailUtil.getAppUrl(request);
            SimpleMailMessage simpleMailMessage = mailUtil.constructResetTokenEmail(appUrl,request.getLocale(), token, user);
            mailSender.send(simpleMailMessage);
        }
        return ResponseEntity.ok().body("You should receive an Password Reset Email shortly");
    }

    @GetMapping("/verifyToken")
    public ResponseEntity showChangePasswordPage(@RequestParam("token") String token) {
        String result = passwordResetTokenService.validatePasswordResetToken(token);
        if(result != null) {
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.ok().body("tokenValid");
        }
    }


}
