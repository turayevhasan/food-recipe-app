package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.Attachment;
import uz.pdp.food_recipe_app.entity.Ingredient;
import uz.pdp.food_recipe_app.entity.Recipe;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.mapper.IngredientMapper;
import uz.pdp.food_recipe_app.payload.ingredient.res.IngredientRes;
import uz.pdp.food_recipe_app.payload.ingredient.req.IngredientUpdateReq;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.ingredient.req.IngredientAddReq;
import uz.pdp.food_recipe_app.repository.AttachmentRepository;
import uz.pdp.food_recipe_app.repository.IngredientRepository;
import uz.pdp.food_recipe_app.repository.RecipeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final AttachmentRepository attachmentRepository;
    private final RecipeRepository recipeRepository;
    private final AttachmentService attachmentService;


    public IngredientRes add(IngredientAddReq req) {
        Attachment photo = attachmentRepository.findById(req.getPhotoId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.FILE_NOT_FOUND));

        Recipe recipe = recipeRepository.findById(req.getRecipeId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.RECIPE_NOT_FOUND));

        Ingredient ingredient = Ingredient.builder()
                .name(req.getName())
                .photoId(req.getPhotoId())
                .weight(req.getWeight())
                .recipe(recipe)
                .build();

        ingredientRepository.save(ingredient); //saved

        return IngredientMapper.entityToRes(ingredient, photo.getFilePath());
    }


    public IngredientRes update(long id, IngredientUpdateReq req) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.INGREDIENT_NOT_FOUND));

        String photoPath;
        if (req.getPhotoId() != null)
            photoPath = attachmentRepository.findById(req.getPhotoId())
                    .orElseThrow(RestException.thew(ErrorTypeEnum.FILE_NOT_FOUND))
                    .getFilePath();
        else
            photoPath = attachmentRepository.findById(ingredient.getPhotoId())
                    .orElseThrow(RestException.thew(ErrorTypeEnum.FILE_NOT_FOUND))
                    .getFilePath();

        if (req.getWeight() != null)
            ingredient.setWeight(req.getWeight());

        if (req.getName() != null)
            ingredient.setName(req.getName());

        return IngredientMapper.entityToRes(ingredient, photoPath);
    }

    public ResBaseMsg delete(long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.INGREDIENT_NOT_FOUND));

        attachmentService.delete(ingredient.getPhotoId()); //deleting photo from file and db
        ingredientRepository.delete(ingredient); //deleting

        return new ResBaseMsg("Ingredient deleted");
    }

    public List<IngredientRes> getAll(long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.RECIPE_NOT_FOUND));

        return recipe.getIngredients().stream()
                .map(ingredient -> {
                    Attachment photo = attachmentRepository.findById(ingredient.getPhotoId())
                            .orElseThrow(RestException.thew(ErrorTypeEnum.FILE_NOT_FOUND));
                    return IngredientMapper.entityToRes(ingredient, photo.getFilePath());
                })
                .toList();
    }
}
