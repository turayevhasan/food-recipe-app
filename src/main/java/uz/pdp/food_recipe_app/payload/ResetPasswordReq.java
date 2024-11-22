package uz.pdp.food_recipe_app.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordReq {
    @NotBlank
    private String email;

    @NotBlank
    private String code;

    @NotBlank
    @Size(min = 6, max = 20)
    private String newPassword;

    @NotBlank
    @Size(min = 6, max = 20)
    private String confirmNewPassword;
}
