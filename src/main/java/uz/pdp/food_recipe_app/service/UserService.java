package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.Attachment;
import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.mapper.UserMapper;
import uz.pdp.food_recipe_app.payload.user.req.ProfileUpdateReq;
import uz.pdp.food_recipe_app.payload.user.req.ResetPasswordReq;
import uz.pdp.food_recipe_app.payload.user.req.UpdatePasswordReq;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.user.res.UserRes;
import uz.pdp.food_recipe_app.repository.AttachmentRepository;
import uz.pdp.food_recipe_app.repository.UserRepository;
import uz.pdp.food_recipe_app.util.GlobalVar;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final CodeService codeService;
    private final AttachmentRepository attachmentRepository;

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
        User user = GlobalVar.getUser();

        if (user == null)
            throw RestException.restThrow(ErrorTypeEnum.USER_NOT_FOUND_OR_DISABLED);

        if (!req.getNewPassword().equals(req.getConfirmNewPassword()))
            throw RestException.restThrow(ErrorTypeEnum.CONFIRM_PASSWORD_NOT_MATCH);

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user); //updated

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

    public UserRes update(ProfileUpdateReq req) {
        if (GlobalVar.getUser() == null)
            throw RestException.restThrow(ErrorTypeEnum.USER_NOT_FOUND_OR_DISABLED);

        User user = GlobalVar.getUser();

        if (req.getPhotoId() != null) {
            if (!attachmentRepository.existsById(req.getPhotoId())) {
                throw RestException.restThrow(ErrorTypeEnum.FILE_NOT_FOUND);
            }
            user.setPhotoId(req.getPhotoId());
        }
        UserMapper.update(user, req); //updating
        userRepository.save(user); //saved

        return UserMapper.entityToRes(user, getPhotoPath(user));
    }

    private String getPhotoPath(User user) {
        if (user.getPhotoId() == null)
            return null;

        return attachmentRepository
                .findById(user.getPhotoId())
                .map(Attachment::getFilePath)
                .orElse(null);
    }
}
