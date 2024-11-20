package uz.pdp.food_recipe_app.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.payload.ConfirmPasswordReq;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.repository.UserRepository;
import uz.pdp.food_recipe_app.util.GlobalVar;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    public ResBaseMsg sendResetPassword(String email) {
        if (!userRepository.existsByEmail(email))
            throw RestException.restThrow(ErrorTypeEnum.USER_NOT_FOUND);

        String body = """
                <form action="http://localhost:8080/api/v1/user/forgot-password" method="POST">
                  <div class="form-group">
                     <label for="email">Email address</label>
                     <input type="email" id="email" aria-describedby="emailHelp">
                     <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
                  </div>
                  <div class="form-group">
                     <label for="password">New Password</label>
                     <input type="password" id="password">
                  </div>
                  <div class="form-group">
                     <label for="passwordConfirm">Confirm Password</label>
                     <input type="password" id="passwordConfirm">
                  </div>
                  <button type="submit" class="btn-primary">Submit</button>
                </form>
                """;

        try {
            mailService.sendMessage(email, body, "Change Your Password", "Forgot Password");
        } catch (MessagingException e) {
            throw RestException.restThrow(ErrorTypeEnum.EMAIL_NOT_VALID);
        }

        return new ResBaseMsg("We have sent link to reset your password!");
    }

    public ResBaseMsg forgotPassword(ConfirmPasswordReq req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(RestException.thew(ErrorTypeEnum.USER_NOT_FOUND));

        if (!req.getPassword().equals(req.getConfirmPassword()))
            throw RestException.restThrow(ErrorTypeEnum.CONFIRM_PASSWORD_NOT_MATCH);

        user.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepository.save(user); //updated

        return new ResBaseMsg("Successfully updated your password!");
    }

    public ResBaseMsg changePassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword))
            throw RestException.restThrow(ErrorTypeEnum.CONFIRM_PASSWORD_NOT_MATCH);

        GlobalVar.getUser().setPassword(passwordEncoder.encode(password));
        userRepository.save(GlobalVar.getUser());

        return new ResBaseMsg("Successfully changed your password!");
    }
}
