package uz.pdp.food_recipe_app.mapper;

import uz.pdp.food_recipe_app.entity.Like;
import uz.pdp.food_recipe_app.payload.like.res.LikeRes;

public interface LikeMapper {
    static LikeRes entityToDto(Like like) {
        return LikeRes.builder()
                .id(like.getId())
                .likeOrDislike(like.getLikeOrDislike())
                .userId(like.getUser().getId())
                .createdAt(like.getCreatedAt())
                .updatedAt(like.getUpdatedAt())
                .build();
    }
}
