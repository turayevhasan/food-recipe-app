package uz.pdp.food_recipe_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.food_recipe_app.payload.base.ApiResult;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.procedure.req.ProcedureAddReq;
import uz.pdp.food_recipe_app.payload.procedure.req.ProcedureUpdateReq;
import uz.pdp.food_recipe_app.payload.procedure.res.ProcedureRes;
import uz.pdp.food_recipe_app.service.ProcedureService;
import uz.pdp.food_recipe_app.util.BaseURI;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.PROCEDURE)
public class ProcedureController {
    private final ProcedureService procedureService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public ApiResult<ResBaseMsg> addProcedure(@RequestBody @Valid ProcedureAddReq req) {
        return ApiResult.successResponse(procedureService.add(req));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get/{id}")
    public ApiResult<ProcedureRes> getProcedure(@PathVariable("id") long id) {
        return ApiResult.successResponse(procedureService.getOne(id));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update/{id}")
    public ApiResult<ProcedureRes> update(@PathVariable("id") long id, @RequestBody ProcedureUpdateReq req){
        return ApiResult.successResponse(procedureService.update(id, req));
    }
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{id}")
    public ApiResult<ResBaseMsg> delete(@PathVariable("id") long id) {
        return ApiResult.successResponse(procedureService.delete(id));
    }

    //get all recipe's procedures
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-all/{recipeId}")
    public ApiResult<List<ProcedureRes>> getAllProcedure(@PathVariable("recipeId") long recipeId) {
        return ApiResult.successResponse(procedureService.getAll(recipeId));
    }
}
