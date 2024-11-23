package uz.pdp.food_recipe_app.payload.recipe.req;

import lombok.Data;

import java.util.UUID;

@Data
public class RecipeUpdateReq {
    private String name;
    private UUID videoId;
    private Long categoryId;
}
