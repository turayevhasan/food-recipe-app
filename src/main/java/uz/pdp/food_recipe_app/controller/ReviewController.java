package uz.pdp.food_recipe_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.food_recipe_app.payload.base.ApiResult;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.review.req.ReviewAddReq;
import uz.pdp.food_recipe_app.payload.review.res.ReviewRes;
import uz.pdp.food_recipe_app.service.ReviewService;
import uz.pdp.food_recipe_app.util.BaseURI;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.REVIEW)

public class ReviewController {
    private final ReviewService reviewService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public ApiResult<ResBaseMsg> addReview(@RequestBody @Valid ReviewAddReq reviewAddReq) {
        return ApiResult.successResponse(reviewService.add(reviewAddReq));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update/{id}")
    public ApiResult<ReviewRes> updateReview(@PathVariable("id") Long id, @RequestParam(required = false) String text) {
        return ApiResult.successResponse(reviewService.update(id, text));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{id}")
    public ApiResult<ResBaseMsg> deleteReview(@PathVariable("id") Long id) {
        return ApiResult.successResponse(reviewService.delete(id));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-all/{recipeId}")
    public ApiResult<List<ReviewRes>> getAllReviews(@PathVariable("recipeId") long recipeId) {
        return ApiResult.successResponse(reviewService.getAllReviews(recipeId));
    }
}
