package uz.pdp.food_recipe_app.payload.ingredient.req;

import lombok.Data;

import java.util.UUID;

@Data
public class IngredientUpdateReq {
    private String name;
    private UUID photoId;
    private Double weight;
}
