package uz.pdp.food_recipe_app.mapper;

import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.payload.user.req.ProfileUpdateReq;
import uz.pdp.food_recipe_app.payload.user.res.UserRes;
import uz.pdp.food_recipe_app.util.GlobalVar;

import static uz.pdp.food_recipe_app.util.CoreUtils.getIfExists;

public interface UserMapper {
    static UserRes entityToRes(User user, String path) {
        return UserRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .photoPath(path)
                .country(user.getCountry())
                .active(user.isActive())
                .deleted(user.isDeleted())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    static void update(User user, ProfileUpdateReq req) {
        user.setFullName(getIfExists(req.getFullName(), user.getFullName()));
        user.setCountry(getIfExists(req.getCountry(), user.getCountry()));
        user.setBio(getIfExists(req.getBio(), user.getBio()));
    }
}
