package uz.pdp.food_recipe_app.payload.auth.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyAccountReq {
    @NotBlank
    private String email;
    @NotBlank
    private String code;
}
