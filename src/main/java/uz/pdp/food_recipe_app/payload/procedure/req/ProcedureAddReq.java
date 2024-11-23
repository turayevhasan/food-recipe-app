package uz.pdp.food_recipe_app.payload.procedure.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProcedureAddReq {
    @NotBlank
    private String text;
    @NotNull
    private Long recipeId;
}
