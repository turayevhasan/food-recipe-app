package uz.pdp.food_recipe_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.food_recipe_app.payload.ConfirmPasswordReq;
import uz.pdp.food_recipe_app.payload.base.ApiResult;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.service.UserService;
import uz.pdp.food_recipe_app.util.BaseURI;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.USER)
public class UserController {
    private final UserService userService;

    @PostMapping("/send-reset-password/{email}")
    public ApiResult<ResBaseMsg> resetPassword(@PathVariable("email") String email) {
        return ApiResult.successResponse(userService.sendResetPassword(email));
    }

    @PostMapping("/forgot-password")
    public ApiResult<ResBaseMsg> forgotPassword(@RequestBody @Valid ConfirmPasswordReq req) {
        return ApiResult.successResponse(userService.forgotPassword(req));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/change-password")
    public ApiResult<ResBaseMsg> changePassword(
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword){
        return ApiResult.successResponse(userService.changePassword(password, confirmPassword));
    }

}
