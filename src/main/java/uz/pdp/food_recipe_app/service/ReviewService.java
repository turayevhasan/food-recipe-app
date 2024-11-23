package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.Recipe;
import uz.pdp.food_recipe_app.entity.Review;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.mapper.ReviewMapper;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.review.req.ReviewAddReq;
import uz.pdp.food_recipe_app.payload.review.res.ReviewRes;
import uz.pdp.food_recipe_app.repository.RecipeRepository;
import uz.pdp.food_recipe_app.repository.ReviewRepository;
import uz.pdp.food_recipe_app.util.GlobalVar;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RecipeRepository recipeRepository;

    public ResBaseMsg add(ReviewAddReq reviewAddReq) {
        if (GlobalVar.getUser() == null) {
            throw RestException.restThrow(ErrorTypeEnum.USER_NOT_FOUND_OR_DISABLED);
        }

        Recipe recipe = recipeRepository.findById(reviewAddReq.getRecipeId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.RECIPE_NOT_FOUND));

        Review review = Review.builder()
                .user(GlobalVar.getUser())
                .text(reviewAddReq.getText())
                .recipe(recipe)
                .build();

        reviewRepository.save(review);

        return new ResBaseMsg("Review successfully sent!");
    }

    public ReviewRes update(Long id, String text) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.REVIEW_NOT_FOUND));

        if (text != null) {
            review.setText(text);
        }

        reviewRepository.save(review);

        return ReviewMapper.entityToDto(review);
    }

    public ResBaseMsg delete(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.REVIEW_NOT_FOUND));

        review.setDeleted(true);

        reviewRepository.save(review);

        return new ResBaseMsg("Review successfully deleted!");
    }

    public List<ReviewRes> getAllReviews(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.RECIPE_NOT_FOUND));

        return recipe.getReviews()
                .stream()
                .map(ReviewMapper::entityToDto)
                .toList();
    }
}
