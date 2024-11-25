package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.Rating;
import uz.pdp.food_recipe_app.entity.Recipe;
import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.mapper.RecipeMapper;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.rating.req.RatingAddReq;
import uz.pdp.food_recipe_app.payload.rating.res.RatingRes;
import uz.pdp.food_recipe_app.payload.recipe.res.RecipeRes;
import uz.pdp.food_recipe_app.repository.RatingRepository;
import uz.pdp.food_recipe_app.repository.RecipeRepository;
import uz.pdp.food_recipe_app.repository.UserRepository;
import uz.pdp.food_recipe_app.util.GlobalVar;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public ResBaseMsg add(RatingAddReq req) {

        if(ratingRepository.existsByUserIdAndRecipeId(GlobalVar.getUser().getId(),req.getRecipeId())){
            throw RestException.restThrow(ErrorTypeEnum.RATING_ALREADY_EXISTS);
        }

        Recipe recipe = recipeRepository.findById(req.getRecipeId()).
                orElseThrow(RestException.thew(ErrorTypeEnum.RECIPE_NOT_FOUND));
        User user = GlobalVar.getUser();

        Rating rating = Rating.builder()
                .stars(req.getStars())
                .recipe(recipe)
                .user(user)
                .build();

        ratingRepository.save(rating);
        return new ResBaseMsg("Rating successfully saved!");
    }

    public RatingRes get(Long recipeId) {
        List<Rating> ratings = ratingRepository.findAllByRecipeId(recipeId);

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.RECIPE_NOT_FOUND));

        RecipeRes recipeRes = RecipeMapper.entityToRes(recipe);

        int stars = ratings.stream().mapToInt(Rating::getStars).sum();


        return RatingRes.builder()
                .stars((double) stars / ratings.size())
                .recipeRes(recipeRes)
                .build();
    }
}
