package uz.pdp.food_recipe_app.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.food_recipe_app.entity.Category;
import uz.pdp.food_recipe_app.entity.Role;
import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.enums.RoleEnum;
import uz.pdp.food_recipe_app.enums.UserStatus;
import uz.pdp.food_recipe_app.repository.CategoryRepository;
import uz.pdp.food_recipe_app.repository.FollowRepository;
import uz.pdp.food_recipe_app.repository.RoleRepository;
import uz.pdp.food_recipe_app.repository.UserRepository;
import uz.pdp.food_recipe_app.service.FollowService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
//        roleLoad();
//        userLoad();
//        categoryLoad();
    }

    private void roleLoad() {
        roleRepository.save(new Role(RoleEnum.USER.name()));
        roleRepository.save(new Role(RoleEnum.ADMIN.name()));
        roleRepository.save(new Role(RoleEnum.MANAGER.name()));
    }

    private void categoryLoad() {
        categoryRepository.save(new Category("Breakfast"));
        categoryRepository.save(new Category("Vegetables"));
        categoryRepository.save(new Category("Dinner"));
        categoryRepository.save(new Category("Fruit"));
        categoryRepository.save(new Category("Lunch"));
        categoryRepository.save(new Category("Chinese"));
        categoryRepository.save(new Category("Spanish"));
    }

    private void userLoad() {
        userRepository.saveAll(List.of(
                User.builder()
                        .fullName("Gordon Ramsay")
                        .email("gordon.ramsay@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .country("United Kingdom")
                        .bio("Famous for Hell's Kitchen and exceptional culinary skills.")
                        .role(roleRepository.findByName(RoleEnum.USER.name()).orElseThrow())
                        .status(UserStatus.ACTIVE)
                        .build(),
                User.builder()
                        .fullName("Jamie Oliver")
                        .email("jamie.oliver@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .country("United Kingdom")
                        .bio("Passionate about healthy cooking and nutrition advocacy.")
                        .role(roleRepository.findByName(RoleEnum.USER.name()).orElseThrow())
                        .status(UserStatus.ACTIVE)
                        .build(),
                User.builder()
                        .fullName("Massimo Bottura")
                        .email("massimo.bottura@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .country("Italy")
                        .bio("Renowned Italian chef and owner of Osteria Francescana.")
                        .role(roleRepository.findByName(RoleEnum.USER.name()).orElseThrow())
                        .status(UserStatus.ACTIVE)
                        .build(),
                User.builder()
                        .fullName("Wolfgang Puck")
                        .email("wolfgang.puck@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .country("United States")
                        .bio("Pioneer of fusion cuisine and a celebrated restaurateur.")
                        .role(roleRepository.findByName(RoleEnum.USER.name()).orElseThrow())
                        .status(UserStatus.ACTIVE)
                        .build(),
                User.builder()
                        .fullName("Alice Waters")
                        .email("alice.waters@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .country("United States")
                        .bio("Advocate for organic food and founder of Chez Panisse.")
                        .role(roleRepository.findByName(RoleEnum.USER.name()).orElseThrow())
                        .status(UserStatus.ACTIVE)
                        .build()
        ));

    }
}
