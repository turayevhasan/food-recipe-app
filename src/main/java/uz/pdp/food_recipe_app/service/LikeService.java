package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.Like;
import uz.pdp.food_recipe_app.entity.Review;
import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.like.req.LikeAddReq;
import uz.pdp.food_recipe_app.payload.like.res.LikeRes;
import uz.pdp.food_recipe_app.repository.LikeRepository;
import uz.pdp.food_recipe_app.repository.ReviewRepository;
import uz.pdp.food_recipe_app.util.GlobalVar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final ReviewRepository reviewRepository;
    private final LikeRepository likeRepository;

    public ResBaseMsg add(LikeAddReq req) {
        User user = GlobalVar.getUser();
        Review review = reviewRepository.findById(req.getReviewId()).orElseThrow(RestException.thew(ErrorTypeEnum.REVIEW_NOT_FOUND));
        Like like = new Like().builder()
                .likeOrDislike(req.getLikeOrDislike())
                .review(review)
                .user(user)
                .build();

        likeRepository.save(like);
        return new ResBaseMsg("successfully added like");
    }

    public ResBaseMsg update(long id, boolean likeOrDislike) {
        Like like = likeRepository.findById(id).orElseThrow(RestException.thew(ErrorTypeEnum.LIKE_NOT_FOUND));
        like.setLikeOrDislike(likeOrDislike);
        likeRepository.save(like);
        return new ResBaseMsg("successfully updated like");
    }

    public LikeRes getOne(long id) {
        Like like = likeRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.LIKE_NOT_FOUND));
        return new LikeRes().builder()
                .likeOrDislike(like.getLikeOrDislike())
                .reviewId(like.getReview().getId())
                .userId(like.getUser().getId())
                .build();
    }

    public List<LikeRes> getAll() {
        return likeRepository.findAll().stream()
                .map(like -> LikeRes.builder()
                        .id(like.getId())
                        .likeOrDislike(like.getLikeOrDislike())
                        .reviewId(like.getReview().getId())
                        .userId(like.getUser().getId())
                        .createdAt(like.getCreatedAt())
                        .updatedAt(like.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public ResBaseMsg delete(long id) {
        if (!likeRepository.existsById(id)) {
            return new ResBaseMsg("like_not_found");
        }
        likeRepository.deleteById(id);
        return new ResBaseMsg("successfully deleted like");
    }
}
