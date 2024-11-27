package uz.pdp.food_recipe_app.mapper;

import uz.pdp.food_recipe_app.entity.Category;
import uz.pdp.food_recipe_app.payload.category.res.CategoryRes;

public interface CategoryMapper {

    static CategoryRes entityToDto(Category category) {
        return CategoryRes.builder()
                .id(category.getId())
                .name(category.getName())
                .photoPath(category.getPhotoPath())
                .deleted(category.isDeleted())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}
