package uz.pdp.food_recipe_app.payload.rating.res;

import lombok.Builder;
import lombok.Data;
import uz.pdp.food_recipe_app.entity.Recipe;
import uz.pdp.food_recipe_app.payload.recipe.res.RecipeRes;
import uz.pdp.food_recipe_app.payload.user.res.UserRes;

@Builder
@Data
public class RatingRes {
    private Double stars;
    private RecipeRes recipeRes;
}
