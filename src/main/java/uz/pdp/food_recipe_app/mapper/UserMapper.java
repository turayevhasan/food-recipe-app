package uz.pdp.food_recipe_app.mapper;

import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.payload.auth.res.UserRes;

public interface UserMapper {

    static UserRes fromEntityToDto(User user) {
        return UserRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .photoPath(null)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
