package uz.pdp.food_recipe_app.payload.ingredient.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class IngredientAddReq {
    @NotBlank
    private String name;

    @NotNull
    private String photoPath;

    @NotNull
    private Double weight;

    @NotNull
    private Long recipeId;
}
