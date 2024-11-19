package uz.pdp.food_recipe_app.payload.category.req;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryAddReq {
    @NotBlank
    private String name;
    @NotNull
    private UUID photoId;
}
