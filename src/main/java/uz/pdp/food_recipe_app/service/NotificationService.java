package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.Following;
import uz.pdp.food_recipe_app.entity.Notification;
import uz.pdp.food_recipe_app.entity.Recipe;
import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.mapper.NotificationMapper;
import uz.pdp.food_recipe_app.payload.notification.req.NotificationUpdateReq;
import uz.pdp.food_recipe_app.payload.notification.res.NotificationRes;
import uz.pdp.food_recipe_app.repository.FollowRepository;
import uz.pdp.food_recipe_app.repository.NotificationRepository;
import uz.pdp.food_recipe_app.repository.RecipeRepository;
import uz.pdp.food_recipe_app.util.GlobalVar;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableAsync
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final RecipeRepository recipeRepository;
    private final FollowRepository followRepository;

    @Async
    public void addRecipeNotification(Recipe recipe) {
        List<Following> allByUserId = followRepository.findAllByFollowingId(recipe.getUser().getId());
        List<Notification> notifications = new ArrayList<>();
        for (Following following : allByUserId) {
            notifications.add(
                    Notification.builder()
                            .title("New Recipe Alert!")
                            .text(recipe.getName())
                            .recipeId(recipe.getId())
                            .read(false)
                            .user(following.getUser())
                            .build()
            );
        }
        notificationRepository.saveAll(notifications);
    }

    public NotificationRes update(long id, NotificationUpdateReq req) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.NOTIFICATION_NOT_FOUND));

        if (req.getRecipeId() != null) {
            if (!recipeRepository.existsById(req.getRecipeId()))
                throw RestException.restThrow(ErrorTypeEnum.RECIPE_NOT_FOUND);
            notification.setRecipeId(req.getRecipeId());
        }

        NotificationMapper.update(notification, req); //updating
        notificationRepository.save(notification); //updated

        return NotificationMapper.fromEntityToDto(notification);
    }

    public NotificationRes getOne(long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.NOTIFICATION_NOT_FOUND));

        if (!notification.getUser().getId().equals(GlobalVar.getUser().getId())) {
            throw RestException.restThrow(ErrorTypeEnum.NOTIFICATION_NOT_FOUND);
        }

        if (notification.getRead().equals(Boolean.FALSE)) {
            notification.setRead(Boolean.TRUE);
            notificationRepository.save(notification); //updated
        }
        return NotificationMapper.fromEntityToDto(notification);
    }

    public List<NotificationRes> getAll(Boolean read) {
        User user = GlobalVar.getUser();
        List<Notification> all;
        if (read == null) {
            all = notificationRepository.findAllByUser(user);
        } else {
            all = notificationRepository.findAllByUserAndRead(user, read);
        }

        return all.stream()
                .map(NotificationMapper::fromEntityToDto)
                .toList();
    }
}
