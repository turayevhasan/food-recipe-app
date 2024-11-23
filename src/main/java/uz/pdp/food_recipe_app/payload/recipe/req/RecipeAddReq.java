package uz.pdp.food_recipe_app.payload.recipe.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RecipeAddReq {
    @NotBlank
    private String name;

    @NotNull
    private UUID videoId;

    @NotNull
    private Long categoryId;
}
