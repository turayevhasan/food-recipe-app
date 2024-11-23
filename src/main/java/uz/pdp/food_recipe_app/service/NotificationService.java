package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.Notification;
import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.mapper.NotificationMapper;
import uz.pdp.food_recipe_app.payload.notification.req.NotificationUpdateReq;
import uz.pdp.food_recipe_app.payload.notification.res.NotificationRes;
import uz.pdp.food_recipe_app.repository.NotificationRepository;
import uz.pdp.food_recipe_app.repository.RecipeRepository;
import uz.pdp.food_recipe_app.util.GlobalVar;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final RecipeRepository recipeRepository;

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

        return NotificationMapper.fromEntityToDto(notification);
    }

    public List<NotificationRes> getAll(Boolean read) {
        if (GlobalVar.getUser() == null)
            throw RestException.restThrow(ErrorTypeEnum.USER_NOT_FOUND_OR_DISABLED);

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
