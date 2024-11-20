package uz.pdp.food_recipe_app.payload.auth.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignUpReq {
    @NotBlank
    private String fullName;

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

}
