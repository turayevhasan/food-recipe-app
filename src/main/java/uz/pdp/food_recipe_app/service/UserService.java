package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.payload.ResetPasswordReq;
import uz.pdp.food_recipe_app.payload.UpdatePasswordReq;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.repository.UserRepository;
import uz.pdp.food_recipe_app.util.GlobalVar;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final CodeService codeService;

    public ResBaseMsg forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(RestException.thew(ErrorTypeEnum.USER_NOT_FOUND));

        if (!user.isActive())
            throw RestException.restThrow(ErrorTypeEnum.USER_NOT_ACTIVATED);

        String code = codeService.generateCode(email);
        String body = String.format("<p class=\"code\">%s</p>", code);
        mailService.sendMessage(email, body, "Receive Your Code", "Forgot Password");

        return new ResBaseMsg("We will sent a code to your email. Please enter that code");
    }

    public ResBaseMsg changePassword(UpdatePasswordReq req) {
        if (GlobalVar.getUser() == null)
            throw RestException.restThrow(ErrorTypeEnum.USER_NOT_FOUND_OR_DISABLED);

        if (!req.getNewPassword().equals(req.getConfirmNewPassword()))
            throw RestException.restThrow(ErrorTypeEnum.CONFIRM_PASSWORD_NOT_MATCH);

        GlobalVar.getUser().setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(GlobalVar.getUser()); //updated

        return new ResBaseMsg("Successfully updated your password!");
    }

    public ResBaseMsg resetPassword(ResetPasswordReq req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(RestException.thew(ErrorTypeEnum.USER_NOT_FOUND));

        if (!user.isActive())
            throw RestException.restThrow(ErrorTypeEnum.USER_NOT_ACTIVATED);

        if (!req.getNewPassword().equals(req.getConfirmNewPassword()))
            throw RestException.restThrow(ErrorTypeEnum.CONFIRM_PASSWORD_NOT_MATCH);

        codeService.checkVerify(req.getEmail(), req.getCode()); //checking

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user); //updated

        return new ResBaseMsg("Successfully updated your password!");
    }
}
