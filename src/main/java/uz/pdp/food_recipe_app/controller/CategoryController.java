package uz.pdp.food_recipe_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.food_recipe_app.payload.base.ApiResult;
import uz.pdp.food_recipe_app.payload.category.req.CategoryAddReq;
import uz.pdp.food_recipe_app.payload.category.req.CategoryUpdateReq;
import uz.pdp.food_recipe_app.payload.category.res.CategoryRes;
import uz.pdp.food_recipe_app.service.CategoryService;
import uz.pdp.food_recipe_app.util.BaseURI;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.CATEGORY)
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public ApiResult<CategoryRes> addCategory(@RequestBody @Valid CategoryAddReq req) {
        return ApiResult.successResponse(categoryService.add(req));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update/{id}")
    public ApiResult<CategoryRes> updateCategory(@PathVariable("id") long id, @RequestBody CategoryUpdateReq req){
        return ApiResult.successResponse(categoryService.update(id, req));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get/{id}")
    public ApiResult<CategoryRes> getCategory(@PathVariable("id") long id){
        return ApiResult.successResponse(categoryService.getOne(id));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-all")
    public ApiResult<List<CategoryRes>> getAllCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name){
        return ApiResult.successResponse(categoryService.getAll(page, size, name));
    }


}
