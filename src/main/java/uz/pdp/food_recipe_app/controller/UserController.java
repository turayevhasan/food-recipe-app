package uz.pdp.food_recipe_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.food_recipe_app.payload.ResetPasswordReq;
import uz.pdp.food_recipe_app.payload.UpdatePasswordReq;
import uz.pdp.food_recipe_app.payload.base.ApiResult;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.service.UserService;
import uz.pdp.food_recipe_app.util.BaseURI;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.USER)
public class UserController {
    private final UserService userService;

    @GetMapping("/forgot-password/{email}")
    public ApiResult<ResBaseMsg> forgotPassword(@PathVariable("email") String email) {
        return ApiResult.successResponse(userService.forgotPassword(email));
    }

    @PostMapping("/reset-password")
    public ApiResult<ResBaseMsg> resetPassword(@RequestBody @Valid ResetPasswordReq req) {
        return ApiResult.successResponse(userService.resetPassword(req));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update-password")
    public ApiResult<ResBaseMsg> changePassword(@RequestBody @Valid UpdatePasswordReq req){
        return ApiResult.successResponse(userService.changePassword(req));
    }

}
