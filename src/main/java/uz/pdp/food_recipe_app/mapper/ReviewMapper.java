package uz.pdp.food_recipe_app.mapper;

import uz.pdp.food_recipe_app.entity.Review;
import uz.pdp.food_recipe_app.payload.review.res.ReviewRes;

public interface ReviewMapper {
    static ReviewRes entityToDto(Review review) {
        return ReviewRes.builder()
                .id(review.getId())
                .text(review.getText())
                .deleted(review.isDeleted())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
