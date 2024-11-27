package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.Procedure;
import uz.pdp.food_recipe_app.entity.Recipe;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.procedure.req.ProcedureAddReq;
import uz.pdp.food_recipe_app.payload.procedure.req.ProcedureUpdateReq;
import uz.pdp.food_recipe_app.payload.procedure.res.ProcedureRes;
import uz.pdp.food_recipe_app.mapper.ProcedureMapper;
import uz.pdp.food_recipe_app.repository.ProcedureRepository;
import uz.pdp.food_recipe_app.repository.RecipeRepository;
import uz.pdp.food_recipe_app.util.GlobalVar;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcedureService {
    private final ProcedureRepository procedureRepository;
    private final RecipeRepository recipeRepository;

    public ResBaseMsg add(ProcedureAddReq req) {
        Recipe recipe = recipeRepository.findById(req.getRecipeId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.RECIPE_NOT_FOUND));

        if (!GlobalVar.getUser().getId().equals(recipe.getUser().getId())) {
            throw RestException.restThrow(ErrorTypeEnum.FORBIDDEN);
        }

        Procedure procedure = new Procedure(req.getText(), recipe);
        procedureRepository.save(procedure); //saved

        return new ResBaseMsg("Procedure added!");
    }


    public ProcedureRes getOne(long id) {
        Procedure procedure = procedureRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.PROCEDURE_NOT_FOUND));

        return ProcedureMapper.entityToRes(procedure);
    }


    public ProcedureRes update(long id, ProcedureUpdateReq req) {
        Procedure procedure = procedureRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.PROCEDURE_NOT_FOUND));

        if (!GlobalVar.getUser().getId().equals(procedure.getRecipe().getUser().getId())) {
            throw RestException.restThrow(ErrorTypeEnum.FORBIDDEN);
        }

        if (req.getText() != null) {
            procedure.setText(req.getText());
        }

        if (req.getTargetProcedureId() != null) {
            String text = procedure.getText();

            Procedure targetProcedure = procedureRepository.findById(req.getTargetProcedureId())
                    .orElseThrow(RestException.thew(ErrorTypeEnum.PROCEDURE_NOT_FOUND));

            if (!GlobalVar.getUser().getId().equals(targetProcedure.getRecipe().getUser().getId())) {
                throw RestException.restThrow(ErrorTypeEnum.FORBIDDEN);
            }

            procedure.setText(targetProcedure.getText());
            targetProcedure.setText(text);
        }
        procedureRepository.save(procedure); //updated

        return ProcedureMapper.entityToRes(procedure);
    }

    public List<ProcedureRes> getAll(long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.RECIPE_NOT_FOUND));

        return recipe.getProcedures().stream()
                .map(ProcedureMapper::entityToRes)
                .toList();
    }

    public ResBaseMsg delete(long id) {
        Procedure procedure = procedureRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.PROCEDURE_NOT_FOUND));

        if (!GlobalVar.getUser().getId().equals(procedure.getRecipe().getUser().getId())) {
            throw RestException.restThrow(ErrorTypeEnum.FORBIDDEN);
        }
        procedureRepository.delete(procedure);//deleting

        return new ResBaseMsg("Procedure deleted!");
    }
}
