package uz.pdp.food_recipe_app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public ApiResult<ResBaseMsg> likeAdd(@RequestBody @Valid LikeAddReq req){
        return ApiResult.successResponse(likeService.add(req));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update/{id}")
    public ApiResult<ResBaseMsg> updateLike(
            @PathVariable("id") long id,
            @RequestParam("like") Boolean like){
        return ApiResult.successResponse(likeService.update(id, like));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-all/{reviewId}")
    public ApiResult<List<LikeRes>> getReviewLikes(@PathVariable("reviewId") long reviewId){
        return ApiResult.successResponse(likeService.getReviewLikes(reviewId));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResult<ResBaseMsg> deleteLike(@PathVariable("id") long id){
        return ApiResult.successResponse(likeService.delete(id));
    }
}
