package uz.pdp.food_recipe_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.food_recipe_app.payload.base.ApiResult;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.saved.SavedRes;
import uz.pdp.food_recipe_app.service.SavedService;
import uz.pdp.food_recipe_app.util.BaseURI;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.SAVED)
public class SavedController {
    private final SavedService savedService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public ApiResult<ResBaseMsg> add(@RequestParam("recipeId") Long recipeId) {
        return ApiResult.successResponse(savedService.add(recipeId));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete")
    public ApiResult<ResBaseMsg> delete(@RequestParam("savedId") Long savedId) {
        return ApiResult.successResponse(savedService.delete(savedId));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get-all")
    public ApiResult<List<SavedRes>> getAll() {
        return ApiResult.successResponse(savedService.getAll());
    }
}
