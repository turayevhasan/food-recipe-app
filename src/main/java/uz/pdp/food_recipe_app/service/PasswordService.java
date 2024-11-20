package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.Password;
import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.repository.PasswordRepository;
import uz.pdp.food_recipe_app.util.CoreUtils;

@Service
@RequiredArgsConstructor
public class PasswordService {
    private final PasswordRepository passwordRepository;

    public String generatePassword(User user) {
        String code = CoreUtils.generateSmsCode();

        Password password = new Password(code, user);
        passwordRepository.save(password);

        return code;
    }
}
