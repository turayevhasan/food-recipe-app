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
import uz.pdp.food_recipe_app.repository.RoleRepository;
import uz.pdp.food_recipe_app.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        //roleLoad();
        //userLoad();
        //categoryLoad();
    }

    private void roleLoad() {
        roleRepository.save(new Role(RoleEnum.USER.name()));
        roleRepository.save(new Role(RoleEnum.ADMIN.name()));
        roleRepository.save(new Role(RoleEnum.MANAGER.name()));
    }

    private void categoryLoad(){
        categoryRepository.save(new Category("Breakfast", null));
        categoryRepository.save(new Category("Vegetables", null));
        categoryRepository.save(new Category("Dinner", null));
        categoryRepository.save(new Category("Fruit", null));
        categoryRepository.save(new Category("Lunch", null));
        categoryRepository.save(new Category("Chinese", null));
        categoryRepository.save(new Category("Spanish", null));
    }

    private void userLoad() {
        userRepository.save(
                User.builder()
                        .fullName("Turaev Hasan")
                        .email("turayevhasan100@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .country("Uzbekistan")
                        .bio("Something ...")
                        .role(roleRepository.findByName(RoleEnum.USER.name()).orElseThrow())
                        .status(UserStatus.ACTIVE)
                        .build()
        );
        userRepository.save(
                User.builder()
                        .fullName("Cristiano Ronaldo")
                        .email("cristiano7@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .country("Portugal")
                        .bio("Something ...")
                        .role(roleRepository.findByName(RoleEnum.USER.name()).orElseThrow())
                        .status(UserStatus.ACTIVE)
                        .build()
        );
        userRepository.save(
                User.builder()
                        .fullName("Lionel Messi")
                        .email("messi10@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .country("Argentina")
                        .bio("Something ...")
                        .role(roleRepository.findByName(RoleEnum.USER.name()).orElseThrow())
                        .status(UserStatus.ACTIVE)
                        .build()
        );
        userRepository.save(
                User.builder()
                        .fullName("Neymar Junior")
                        .email("neymar11@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .country("Brazil")
                        .bio("Something ...")
                        .role(roleRepository.findByName(RoleEnum.USER.name()).orElseThrow())
                        .status(UserStatus.ACTIVE)
                        .build()
        );
        userRepository.save(
                User.builder()
                        .fullName("Killian Mbappe")
                        .email("killian7@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .country("France")
                        .bio("Something ...")
                        .role(roleRepository.findByName(RoleEnum.USER.name()).orElseThrow())
                        .status(UserStatus.ACTIVE)
                        .build()
        );
        userRepository.save(
                User.builder()
                        .fullName("Erling Haaland")
                        .email("haaland9@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .country("Norwegian")
                        .bio("Something ...")
                        .role(roleRepository.findByName(RoleEnum.USER.name()).orElseThrow())
                        .status(UserStatus.ACTIVE)
                        .build()
        );
    }
}
