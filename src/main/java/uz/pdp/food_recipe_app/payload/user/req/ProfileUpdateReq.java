package uz.pdp.food_recipe_app.payload.user.req;

import lombok.Data;

import java.util.UUID;

@Data
public class ProfileUpdateReq {
    private String fullName;
    private String country;
    private String bio;
    private UUID photoId;
}
