package uz.pdp.food_recipe_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.food_recipe_app.payload.auth.res.UserRes;
import uz.pdp.food_recipe_app.payload.base.ApiResult;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.category.req.CategoryAddReq;
import uz.pdp.food_recipe_app.payload.category.req.CategoryUpdateReq;
import uz.pdp.food_recipe_app.payload.category.res.CategoryRes;
import uz.pdp.food_recipe_app.service.CategoryService;
import uz.pdp.food_recipe_app.service.FollowService;
import uz.pdp.food_recipe_app.util.BaseURI;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.FOLLOW)
public class FollowController {

    private final FollowService followService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/following/{id}")
    public ApiResult<ResBaseMsg> following(@PathVariable UUID id) {
        return ApiResult.successResponse(followService.following(id));
    }

    @DeleteMapping("/delete-following/{id}")
    public ApiResult<ResBaseMsg> deleteFollowing(@PathVariable("id") UUID id){
        return ApiResult.successResponse(followService.deleteFollowing(id));
    }
    @DeleteMapping("/delete-follower/{id}")
    public ApiResult<ResBaseMsg> deleteFollower(@PathVariable("id") UUID id){
        return ApiResult.successResponse(followService.deleteFollower(id));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-all-followings")
    public ApiResult<List<UserRes>> getAllFollowings(){
        return ApiResult.successResponse(followService.getAllFollowings());
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-all-followers")
    public ApiResult<List<UserRes>> getAllFollowers(){
        return ApiResult.successResponse(followService.getAllFollowers());
    }

}
