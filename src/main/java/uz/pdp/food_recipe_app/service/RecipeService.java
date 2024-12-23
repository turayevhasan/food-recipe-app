package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.Category;
import uz.pdp.food_recipe_app.entity.Recipe;
import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.enums.TimeType;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.mapper.RecipeMapper;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.recipe.req.RecipeAddReq;
import uz.pdp.food_recipe_app.payload.recipe.res.RecipeRes;
import uz.pdp.food_recipe_app.payload.recipe.req.RecipeUpdateReq;
import uz.pdp.food_recipe_app.repository.*;
import uz.pdp.food_recipe_app.util.GlobalVar;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentService attachmentService;
    private final NotificationService notificationService;

    public RecipeRes add(RecipeAddReq req) {
        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

        if (!attachmentRepository.existsById(req.getVideoId()))
            throw RestException.restThrow(ErrorTypeEnum.VIDEO_NOT_FOUND);

        Recipe recipe = Recipe.builder()
                .name(req.getName())
                .category(category)
                .videoId(req.getVideoId())
                .user(GlobalVar.getUser())
                .build();

        recipeRepository.save(recipe); //saving

        notificationService.addRecipeNotification(recipe);

        return RecipeMapper.entityToRes(recipe);
    }

    public RecipeRes update(long id, RecipeUpdateReq req) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.RECIPE_NOT_FOUND));

        if (!GlobalVar.getUser().getId().equals(recipe.getUser().getId())) {
            throw RestException.restThrow(ErrorTypeEnum.FORBIDDEN);
        }
        if (req.getCategoryId() != null) {
            Category category = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

            recipe.setCategory(category);
        }

        if (req.getVideoId() != null) {
            if (!attachmentRepository.existsById(req.getVideoId()))
                throw RestException.restThrow(ErrorTypeEnum.VIDEO_NOT_FOUND);

            recipe.setVideoId(req.getVideoId());
        }

        if (req.getName() != null) {
            recipe.setName(req.getName());
        }

        recipeRepository.save(recipe); //updated

        return RecipeMapper.entityToRes(recipe);
    }

    public RecipeRes getOne(long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.RECIPE_NOT_FOUND));

        return RecipeMapper.entityToRes(recipe);
    }

    public List<RecipeRes> getAll(int page, int size, String name, TimeType timeType, Integer rate, Long categoryId) {
        Pageable pageable = PageRequest.of(page, size);

        String timeTypeStr = timeType != null ? timeType.name() : "ALL";

        return recipeRepository.findAllByFilters(name, categoryId, rate, timeTypeStr, pageable)
                .stream()
                .map(RecipeMapper::entityToRes)
                .toList();
    }

    public List<RecipeRes> myRecipes() {
        if (GlobalVar.getUser() == null)
            throw RestException.restThrow(ErrorTypeEnum.USER_NOT_FOUND_OR_DISABLED);

        return GlobalVar.getUser()
                .getRecipes().stream()
                .map(RecipeMapper::entityToRes)
                .toList();
    }

    public ResBaseMsg delete(long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.RECIPE_NOT_FOUND));

        if (!GlobalVar.getUser().getId().equals(recipe.getUser().getId())) {
            throw RestException.restThrow(ErrorTypeEnum.FORBIDDEN);
        }

        attachmentService.delete(recipe.getVideoId()); //deleting video from file and db
        recipe.setDeleted(true); //set deleted true
        recipeRepository.save(recipe); //saved

        return new ResBaseMsg("Recipe deleted!");
    }
}
