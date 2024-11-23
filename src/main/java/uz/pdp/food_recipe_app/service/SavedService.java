package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.Recipe;
import uz.pdp.food_recipe_app.entity.Saved;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.mapper.SavedMapper;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.saved.SavedRes;
import uz.pdp.food_recipe_app.repository.RecipeRepository;
import uz.pdp.food_recipe_app.repository.SavedRepository;
import uz.pdp.food_recipe_app.util.GlobalVar;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedService {
    private final SavedRepository savedRepository;
    private final RecipeRepository recipeRepository;

    public ResBaseMsg add(Long recipeId) {
        if (GlobalVar.getUser() == null) {
            throw RestException.restThrow(ErrorTypeEnum.USER_NOT_FOUND_OR_DISABLED);
        }

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.RECIPE_NOT_FOUND));

        Saved saved = new Saved(recipe, GlobalVar.getUser());

        savedRepository.save(saved);

        return new ResBaseMsg("Recipe successfully saved!");
    }


    public ResBaseMsg delete(Long savedId) {
        Saved saved = savedRepository.findById(savedId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.SAVED_RECIPE_NOT_FOUND));

        savedRepository.delete(saved);

        return new ResBaseMsg("Recipe successfully deleted!");
    }


    public List<SavedRes> getAll() {
        if (GlobalVar.getUser() == null) {
            throw RestException.restThrow(ErrorTypeEnum.USER_NOT_FOUND_OR_DISABLED);
        }

        return GlobalVar.getUser()
                .getSavings()
                .stream()
                .map(SavedMapper::entityToRes)
                .toList();
    }
}
