package uz.pdp.food_recipe_app.payload.notification.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.food_recipe_app.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationRes {
    private Long id;
    private String title;
    private String text;
    private Boolean read;
    private UUID recipeId;
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
