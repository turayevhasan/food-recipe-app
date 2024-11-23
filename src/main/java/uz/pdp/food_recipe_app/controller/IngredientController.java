package uz.pdp.food_recipe_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.food_recipe_app.payload.ingredient.res.IngredientRes;
import uz.pdp.food_recipe_app.payload.ingredient.req.IngredientUpdateReq;
import uz.pdp.food_recipe_app.payload.base.ApiResult;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.ingredient.req.IngredientAddReq;
import uz.pdp.food_recipe_app.service.IngredientService;
import uz.pdp.food_recipe_app.util.BaseURI;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.INGREDIENT)
public class IngredientController {
    private final IngredientService ingredientService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public ApiResult<IngredientRes> create(@RequestBody @Valid IngredientAddReq req) {
        return ApiResult.successResponse(ingredientService.add(req));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update/{id}")
    public ApiResult<IngredientRes> update(@PathVariable("id") long id, IngredientUpdateReq req) {
        return ApiResult.successResponse(ingredientService.update(id, req));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{id}")
    public ApiResult<ResBaseMsg> delete(@PathVariable("id") long id) {
        return ApiResult.successResponse(ingredientService.delete(id));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-all/{recipeId}")
    public ApiResult<List<IngredientRes>> getAll(@PathVariable("recipeId") long recipeId) {
        return ApiResult.successResponse(ingredientService.getAll(recipeId));
    }
}
