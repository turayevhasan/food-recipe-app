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
import uz.pdp.food_recipe_app.util.GlobalVar;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static uz.pdp.food_recipe_app.util.CoreUtils.getIfExists;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final AttachmentService attachmentService;

    public IngredientRes add(IngredientAddReq req) {
        Recipe recipe = recipeRepository.findById(req.getRecipeId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.RECIPE_NOT_FOUND));

        if (!GlobalVar.getUser().getId().equals(recipe.getUser().getId())) {
            throw RestException.restThrow(ErrorTypeEnum.FORBIDDEN);
        }

        if (!Files.exists(Path.of(req.getPhotoPath()))) {
            throw RestException.restThrow(ErrorTypeEnum.FILE_NOT_FOUND);
        }

        Ingredient ingredient = Ingredient.builder()
                .name(req.getName())
                .photoPath(req.getPhotoPath())
                .weight(req.getWeight())
                .recipe(recipe)
                .build();

        ingredientRepository.save(ingredient); //saved
        return IngredientMapper.entityToRes(ingredient);
    }


    public IngredientRes update(long id, IngredientUpdateReq req) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.INGREDIENT_NOT_FOUND));

        if (!GlobalVar.getUser().getId().equals(ingredient.getRecipe().getUser().getId())) {
            throw RestException.restThrow(ErrorTypeEnum.FORBIDDEN);
        }

        if (req.getPhotoPath() != null) {
            if (!Files.exists(Path.of(req.getPhotoPath()))) {
                throw RestException.restThrow(ErrorTypeEnum.FILE_NOT_FOUND);
            }
            ingredient.setPhotoPath(req.getPhotoPath());
        }

        ingredient.setWeight(getIfExists(req.getWeight(), ingredient.getWeight()));
        ingredient.setName(getIfExists(req.getName(), ingredient.getName()));

        ingredientRepository.save(ingredient);  //updated
        return IngredientMapper.entityToRes(ingredient);
    }

    public ResBaseMsg delete(long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.INGREDIENT_NOT_FOUND));

        if (!GlobalVar.getUser().getId().equals(ingredient.getRecipe().getUser().getId())) {
            throw RestException.restThrow(ErrorTypeEnum.FORBIDDEN);
        }

        attachmentService.deleteByPath(ingredient.getPhotoPath()); //deleting photo from file and db
        ingredientRepository.delete(ingredient); //deleting

        return new ResBaseMsg("Ingredient deleted");
    }

    public List<IngredientRes> getAll(long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.RECIPE_NOT_FOUND));

        return recipe.getIngredients().stream()
                .map(IngredientMapper::entityToRes)
                .toList();
    }
}
