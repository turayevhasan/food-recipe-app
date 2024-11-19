package uz.pdp.food_recipe_app.payload.auth.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class SignInReq {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

}
