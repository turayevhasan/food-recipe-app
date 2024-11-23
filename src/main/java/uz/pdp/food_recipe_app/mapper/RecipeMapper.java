package uz.pdp.food_recipe_app.mapper;

import uz.pdp.food_recipe_app.entity.Recipe;
import uz.pdp.food_recipe_app.payload.recipe.res.RecipeRes;


public interface RecipeMapper {
    static RecipeRes entityToRes(Recipe recipe) {
        return RecipeRes.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .videoId(recipe.getVideoId())
                .deleted(recipe.isDeleted())
                .createdAt(recipe.getCreatedAt())
                .updatedAt(recipe.getUpdatedAt())
                .build();
    }
}
