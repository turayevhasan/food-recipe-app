package uz.pdp.food_recipe_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.food_recipe_app.payload.base.ApiResult;
import uz.pdp.food_recipe_app.payload.notification.req.NotificationUpdateReq;
import uz.pdp.food_recipe_app.payload.notification.res.NotificationRes;
import uz.pdp.food_recipe_app.service.NotificationService;
import uz.pdp.food_recipe_app.util.BaseURI;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.NOTIFICATION)
public class NotificationController {

    private final NotificationService notificationService;

    @PutMapping("/update/{id}")
    public ApiResult<NotificationRes> updateCategory(@PathVariable("id") long id, @RequestBody NotificationUpdateReq req){
        return ApiResult.successResponse(notificationService.update(id, req));
    }

    @GetMapping("/get/{id}")
    public ApiResult<NotificationRes> getCategory(@PathVariable("id") long id){
        return ApiResult.successResponse(notificationService.getOne(id));
    }

    @GetMapping("/get-all")
    public ApiResult<List<NotificationRes>> getAllCategory(
            @RequestParam(required = false) Boolean read){
        return ApiResult.successResponse(notificationService.getAll(read));
    }

}
