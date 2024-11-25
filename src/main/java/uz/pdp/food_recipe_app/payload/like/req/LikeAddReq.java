package uz.pdp.food_recipe_app.payload.like.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LikeAddReq {
    @NotNull
    private Boolean likeOrDislike;

    @NotNull
    private Long reviewId;

    @NotNull
    private Long userId;


}
