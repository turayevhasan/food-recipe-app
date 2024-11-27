package uz.pdp.food_recipe_app.payload.rating.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;
import uz.pdp.food_recipe_app.entity.Recipe;
import uz.pdp.food_recipe_app.payload.recipe.res.RecipeRes;
import uz.pdp.food_recipe_app.payload.user.res.UserRes;
import uz.pdp.food_recipe_app.util.FormatPatterns;

import java.time.LocalDateTime;

@Builder
@Data
public class RatingRes {
    private Double stars;
}
