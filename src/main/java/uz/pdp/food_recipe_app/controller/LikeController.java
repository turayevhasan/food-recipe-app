package uz.pdp.food_recipe_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.food_recipe_app.payload.base.ApiResult;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.like.req.LikeAddReq;
import uz.pdp.food_recipe_app.payload.like.res.LikeRes;
import uz.pdp.food_recipe_app.service.LikeService;
import uz.pdp.food_recipe_app.util.BaseURI;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.LIKE)
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/add")
    public ApiResult<ResBaseMsg> likeAdd(@RequestBody @Valid LikeAddReq req){
        return ApiResult.successResponse(likeService.add(req));
    }

    @PutMapping("/update/{id}/{like}")
    public ApiResult<ResBaseMsg> updateLike(@PathVariable("id") long id, @PathVariable("like") boolean like){
        return ApiResult.successResponse(likeService.update(id, like));
    }

    @GetMapping("/get/{id}")
    public ApiResult<LikeRes> getLike(@PathVariable("id") long id){
        return ApiResult.successResponse(likeService.getOne(id));
    }

    @GetMapping("/get-all")
    public ApiResult<List<LikeRes>> getAllLikes(){
        return ApiResult.successResponse(likeService.getAll());
    }

    @DeleteMapping("/delete/{id}")
    public ApiResult<ResBaseMsg> deleteLike(@PathVariable("id") long id){
        return ApiResult.successResponse(likeService.delete(id));
    }


}
