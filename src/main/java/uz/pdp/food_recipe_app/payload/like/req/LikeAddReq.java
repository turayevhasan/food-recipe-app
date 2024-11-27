package uz.pdp.food_recipe_app.payload.like.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LikeAddReq {
    @NotNull
    private Boolean likeOrDislike;

    @NotNull
    private Long reviewId;
}
