package uz.pdp.food_recipe_app.mapper;

import uz.pdp.food_recipe_app.entity.Saved;
import uz.pdp.food_recipe_app.payload.saved.res.SavedRes;

public interface SavedMapper {
    static SavedRes entityToRes(Saved saved) {
        return SavedRes.builder()
                .id(saved.getId())
                .recipe(RecipeMapper.entityToRes(saved.getRecipe()))
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();
    }
}
