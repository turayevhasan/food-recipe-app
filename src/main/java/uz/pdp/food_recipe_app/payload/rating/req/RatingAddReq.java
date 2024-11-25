package uz.pdp.food_recipe_app.payload.rating.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RatingAddReq {
    @Min(1)
    @Max(5)
    @NotNull
    private Integer stars;
    @NotNull
    private Long recipeId;
}
