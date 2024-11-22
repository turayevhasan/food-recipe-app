package uz.pdp.food_recipe_app.mapper;

import uz.pdp.food_recipe_app.entity.Notification;
import uz.pdp.food_recipe_app.payload.notification.req.NotificationUpdateReq;
import uz.pdp.food_recipe_app.payload.notification.res.NotificationRes;

import static uz.pdp.food_recipe_app.util.CoreUtils.getIfExists;

public interface NotificationMapper {
    static NotificationRes fromEntityToDto(Notification notification) {
        return NotificationRes.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .text(notification.getText())
                .read(notification.getRead())
                .recipeId(notification.getRecipeId())
                .user(notification.getUser())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .build();
    }


    static void update(Notification notification, NotificationUpdateReq req) {
        notification.setRead(getIfExists(req.getRead(), notification.getRead()));
        notification.setTitle(getIfExists(req.getTitle(), notification.getTitle()));
        notification.setText(getIfExists(req.getText(), notification.getText()));
        notification.setRecipeId(getIfExists(req.getRecipeId(), notification.getRecipeId()));
    }
}
