package uz.pdp.food_recipe_app.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.pdp.food_recipe_app.entity.Role;
import uz.pdp.food_recipe_app.enums.RoleEnum;
import uz.pdp.food_recipe_app.repository.RoleRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        //roleLoad();
    }

    private void roleLoad(){
        roleRepository.save(new Role(RoleEnum.USER.name()));
        roleRepository.save(new Role(RoleEnum.ADMIN.name()));
        roleRepository.save(new Role(RoleEnum.MANAGER.name()));
    }
}
