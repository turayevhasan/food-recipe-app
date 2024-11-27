package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.Rating;
import uz.pdp.food_recipe_app.entity.Recipe;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.rating.req.RatingAddReq;
import uz.pdp.food_recipe_app.repository.RatingRepository;
import uz.pdp.food_recipe_app.repository.RecipeRepository;
import uz.pdp.food_recipe_app.util.GlobalVar;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final RecipeRepository recipeRepository;

    public ResBaseMsg add(RatingAddReq req) {
        if (ratingRepository.existsByUserIdAndRecipeId(GlobalVar.getUser().getId(), req.getRecipeId())) {
            throw RestException.restThrow(ErrorTypeEnum.RATING_ALREADY_EXISTS);
        }

        Recipe recipe = recipeRepository.findById(req.getRecipeId()).
                orElseThrow(RestException.thew(ErrorTypeEnum.RECIPE_NOT_FOUND));

        Rating rating = Rating.builder()
                .stars(req.getStars())
                .recipe(recipe)
                .user(GlobalVar.getUser())
                .build();

        ratingRepository.save(rating);
        return new ResBaseMsg("Rating successfully saved!");
    }

    public Double getRecipeRating(Long recipeId) {
        List<Rating> ratings = ratingRepository.findAllByRecipeId(recipeId);
        int stars = ratings.stream().mapToInt(Rating::getStars).sum();
        return (double) stars / ratings.size();
    }
}
