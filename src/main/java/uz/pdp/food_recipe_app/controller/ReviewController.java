package uz.pdp.food_recipe_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.food_recipe_app.entity.Review;
import uz.pdp.food_recipe_app.payload.base.ApiResult;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.review.req.ReviewAddReq;
import uz.pdp.food_recipe_app.service.ReviewService;
import uz.pdp.food_recipe_app.util.BaseURI;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.REVIEW)

public class ReviewController {
    private final ReviewService reviewService;

    public ApiResult<ResBaseMsg> addReview(@RequestBody @Valid ReviewAddReq reviewAddReq) {
        return ApiResult.successResponse(reviewService.add(reviewAddReq));
    }
}
