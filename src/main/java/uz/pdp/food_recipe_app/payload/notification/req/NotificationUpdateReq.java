package uz.pdp.food_recipe_app.payload.notification.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationUpdateReq {
    private Boolean read;
    private String title;
    private String text;
    private UUID recipeId;
}
