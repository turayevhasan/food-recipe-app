package uz.pdp.food_recipe_app.payload.category.req;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryUpdateReq {
    private String name;
    private UUID photoId;
}
