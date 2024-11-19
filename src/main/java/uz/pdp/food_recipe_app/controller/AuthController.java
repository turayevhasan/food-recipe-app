package uz.pdp.food_recipe_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.food_recipe_app.payload.auth.req.RefreshTokenReq;
import uz.pdp.food_recipe_app.payload.auth.req.SignInReq;
import uz.pdp.food_recipe_app.payload.auth.req.SignUpReq;
import uz.pdp.food_recipe_app.payload.auth.res.SignInRes;
import uz.pdp.food_recipe_app.payload.auth.res.TokenDto;
import uz.pdp.food_recipe_app.payload.base.ApiResult;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.service.AuthService;
import uz.pdp.food_recipe_app.util.BaseURI;


@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.AUTH)
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ApiResult<ResBaseMsg> signUp(@RequestBody @Valid SignUpReq req) {
        return ApiResult.successResponse(authService.signUp(req));
    }

    @GetMapping( "/activate/{email}")
    public ApiResult<ResBaseMsg> verificationEmail(@PathVariable String email) {
        return ApiResult.successResponse(authService.verifyEmail(email));
    }

    @PostMapping("/sign-in")
    ApiResult<SignInRes> signIn(@Valid @RequestBody SignInReq req) {
        return ApiResult.successResponse(authService.signIn(req));
    }

    @GetMapping("/token-refresh")
    ApiResult<TokenDto> refreshToken(@Valid @RequestBody RefreshTokenReq req) {
        return ApiResult.successResponse(authService.refreshToken(req));
    }
}
