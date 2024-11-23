package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.review.req.ReviewAddReq;
import uz.pdp.food_recipe_app.repository.RecipeRepository;
import uz.pdp.food_recipe_app.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RecipeRepository recipeRepository;

//    public ResBaseMsg add(ReviewAddReq reviewAddReq) {
//        recipeRepository.findById(reviewAddReq.getRecipeId())
//                .orElseThrow(RestException.thew(ErrorTypeEnum.RECIPE_NOT_FOUND));
//    }
}
