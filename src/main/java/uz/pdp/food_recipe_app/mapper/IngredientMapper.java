package uz.pdp.food_recipe_app.mapper;

import uz.pdp.food_recipe_app.entity.Ingredient;
import uz.pdp.food_recipe_app.payload.ingredient.res.IngredientRes;

public interface IngredientMapper {
    static IngredientRes entityToRes(Ingredient ingredient, String photoPath) {
        return IngredientRes.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .photoPath(photoPath)
                .weight(ingredient.getWeight())
                .createdAt(ingredient.getCreatedAt())
                .updatedAt(ingredient.getUpdatedAt())
                .build();
    }
}
