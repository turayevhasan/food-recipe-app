package uz.pdp.food_recipe_app.component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.food_recipe_app.entity.Category;
import uz.pdp.food_recipe_app.entity.Following;
import uz.pdp.food_recipe_app.entity.Role;
import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.enums.RoleEnum;
import uz.pdp.food_recipe_app.enums.UserStatus;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.repository.CategoryRepository;
import uz.pdp.food_recipe_app.repository.FollowRepository;
import uz.pdp.food_recipe_app.repository.RoleRepository;
import uz.pdp.food_recipe_app.repository.UserRepository;
import uz.pdp.food_recipe_app.service.FollowService;
import uz.pdp.food_recipe_app.util.GlobalVar;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final FollowRepository followRepository;
    private final FollowService followService;

    @Override
    public void run(String... args) throws Exception {
//        roleLoad();
//        userLoad();
//        categoryLoad();
//        followingLoad();
//        followingDeleteLoad();
    }
    @Transactional
    public void followingDeleteLoad() {
        User user = GlobalVar.getUser();
        Following following = followRepository.findByUserIdAndFollowingId(UUID.fromString("c3826cef-525c-4d4c-8353-040d7ab74843"), UUID.fromString("f84f4f1a-dfad-45a8-bc4e-0cc1bd0d59b8"))
                .orElseThrow(RestException.thew(ErrorTypeEnum.FOLLOWING_NOT_FOUND));
        followRepository.deleteById(following.getId());
    }

    private void followingLoad() {
        User user1 = userRepository.findById(UUID.fromString("c3826cef-525c-4d4c-8353-040d7ab74843"))
                .orElseThrow(null);

        User user2 = userRepository.findById(UUID.fromString("324b57cc-d0b1-47ad-8538-14999fc2add9"))
                .orElseThrow(null);

        User user3 = userRepository.findById(UUID.fromString("cd16b261-ec70-4640-b4f5-8582999ee3ac"))
                .orElseThrow(null);

        Following following1 = Following.builder().user(user1).following(user2).build();
        Following following2 = Following.builder().user(user1).following(user3).build();
        Following following3 = Following.builder().user(user2).following(user1).build();
        Following following4 = Following.builder().user(user3).following(user2).build();
        followRepository.save(following1);
        followRepository.save(following2);
        followRepository.save(following3);
        followRepository.save(following4);


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
