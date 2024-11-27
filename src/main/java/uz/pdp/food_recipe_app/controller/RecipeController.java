package uz.pdp.food_recipe_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.food_recipe_app.enums.TimeType;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.recipe.req.RecipeAddReq;
import uz.pdp.food_recipe_app.payload.recipe.res.RecipeRes;
import uz.pdp.food_recipe_app.payload.recipe.req.RecipeUpdateReq;
import uz.pdp.food_recipe_app.payload.base.ApiResult;
import uz.pdp.food_recipe_app.service.RecipeService;
import uz.pdp.food_recipe_app.util.BaseURI;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.RECIPE)
public class RecipeController {
    private final RecipeService recipeService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public ApiResult<RecipeRes> addRecipe(@RequestBody @Valid RecipeAddReq req) {
        return ApiResult.successResponse(recipeService.add(req));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update/{id}")
    public ApiResult<RecipeRes> updateRecipe(@PathVariable("id") long id, @RequestBody RecipeUpdateReq req){
        return ApiResult.successResponse(recipeService.update(id, req));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get/{id}")
    public ApiResult<RecipeRes> getRecipe(@PathVariable("id") long id){
        return ApiResult.successResponse(recipeService.getOne(id));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{id}")
    public ApiResult<ResBaseMsg> deleteRecipe(@PathVariable("id") long id){
        return ApiResult.successResponse(recipeService.delete(id));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-all")
    public ApiResult<List<RecipeRes>> filterRecipe(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) TimeType timeType,
            @RequestParam(required = false) Integer rate,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String name){
        return ApiResult.successResponse(recipeService.getAll(page, size, name, timeType, rate, categoryId));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my-recipes")
    public ApiResult<List<RecipeRes>> getAllMyRecipes(){
        return ApiResult.successResponse(recipeService.myRecipes());
    }
}
