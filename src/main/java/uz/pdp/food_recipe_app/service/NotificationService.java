package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.Notification;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.mapper.NotificationMapper;
import uz.pdp.food_recipe_app.payload.notification.req.NotificationUpdateReq;
import uz.pdp.food_recipe_app.payload.notification.res.NotificationRes;
import uz.pdp.food_recipe_app.repository.NotificationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationRes update(long id, NotificationUpdateReq req) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.NOTIFICATION_NOT_FOUND));
        NotificationMapper.update(notification, req);
        notificationRepository.save(notification);
        return NotificationMapper.fromEntityToDto(notification);
    }

    public NotificationRes getOne(long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.NOTIFICATION_NOT_FOUND));

        return NotificationMapper.fromEntityToDto(notification);
    }

    public List<NotificationRes> getAll(Boolean read) {
        List<Notification> all;
        if (read == null) {
             all = notificationRepository.findAll();
        }else {
            all = notificationRepository.findAllByRead(read);
        }

        return all
                .stream()
                .map(NotificationMapper::fromEntityToDto)
                .toList();
    }
}
