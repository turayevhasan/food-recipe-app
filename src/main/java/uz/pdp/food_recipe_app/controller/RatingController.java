package uz.pdp.food_recipe_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.food_recipe_app.payload.base.ApiResult;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.rating.req.RatingAddReq;
import uz.pdp.food_recipe_app.payload.rating.res.RatingRes;
import uz.pdp.food_recipe_app.service.RatingService;
import uz.pdp.food_recipe_app.util.BaseURI;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.RATING)
public class RatingController {
    private final RatingService ratingService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public ApiResult<ResBaseMsg> addRating(@RequestBody @Valid RatingAddReq req) {
        return ApiResult.successResponse(ratingService.add(req));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/get/{recipeId}")
    public ApiResult<Double> getRecipeRating(@PathVariable("recipeId") Long recipeId) {
        return ApiResult.successResponse(ratingService.getRecipeRating(recipeId));
    }

}
