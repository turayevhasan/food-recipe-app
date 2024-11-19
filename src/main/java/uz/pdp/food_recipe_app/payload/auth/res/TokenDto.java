package uz.pdp.food_recipe_app.payload.auth.res;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    private String accessToken;
    private Long accessTokenExpire;
    private String refreshToken;
    private Long refreshTokenExpire;
}
