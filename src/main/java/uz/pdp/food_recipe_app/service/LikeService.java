package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.Like;
import uz.pdp.food_recipe_app.entity.Review;
import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.mapper.LikeMapper;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.like.req.LikeAddReq;
import uz.pdp.food_recipe_app.payload.like.res.LikeRes;
import uz.pdp.food_recipe_app.repository.LikeRepository;
import uz.pdp.food_recipe_app.repository.ReviewRepository;
import uz.pdp.food_recipe_app.util.GlobalVar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static uz.pdp.food_recipe_app.util.CoreUtils.getIfExists;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final ReviewRepository reviewRepository;
    private final LikeRepository likeRepository;

    public ResBaseMsg add(LikeAddReq req) {
        User user = GlobalVar.getUser();

        Review review = reviewRepository.findById(req.getReviewId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.REVIEW_NOT_FOUND));

        Like like = Like.builder()
                .likeOrDislike(req.getLikeOrDislike())
                .review(review)
                .user(user)
                .build();

        likeRepository.save(like);  //saved
        return new ResBaseMsg("Like saved");
    }

    public ResBaseMsg update(long id, Boolean likeOrDislike) {
        Like like = likeRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.LIKE_NOT_FOUND));

        if (!GlobalVar.getUser().getId().equals(like.getUser().getId())) {
            throw RestException.restThrow(ErrorTypeEnum.FORBIDDEN);
        }

        like.setLikeOrDislike(getIfExists(likeOrDislike, like.getLikeOrDislike()));
        likeRepository.save(like);  //updated

        return new ResBaseMsg("Like updated");
    }

    public List<LikeRes> getReviewLikes(long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.REVIEW_NOT_FOUND));

        return review.getLikes()
                .stream()
                .map(LikeMapper::entityToDto)
                .toList();
    }

    public ResBaseMsg delete(long id) {
        Like like = likeRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.LIKE_NOT_FOUND));

        if(!GlobalVar.getUser().getId().equals(like.getUser().getId()))
            throw RestException.restThrow(ErrorTypeEnum.FORBIDDEN);

        likeRepository.delete(like);
        return new ResBaseMsg("Like deleted");
    }
}
