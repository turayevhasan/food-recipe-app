package uz.pdp.food_recipe_app.payload.user.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePasswordReq {
    @NotBlank
    @Size(min = 6, max = 20)
    private String newPassword;

    @NotBlank
    @Size(min = 6, max = 20)
    private String confirmNewPassword;
}
