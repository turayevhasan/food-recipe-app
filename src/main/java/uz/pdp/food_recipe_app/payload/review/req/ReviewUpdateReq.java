package uz.pdp.food_recipe_app.payload.review.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdateReq {

    @NotBlank
    private String text;
}
