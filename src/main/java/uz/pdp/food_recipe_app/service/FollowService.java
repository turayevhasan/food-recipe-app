package uz.pdp.food_recipe_app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.Attachment;
import uz.pdp.food_recipe_app.entity.Following;
import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.mapper.UserMapper;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
import uz.pdp.food_recipe_app.payload.user.res.UserRes;
import uz.pdp.food_recipe_app.repository.AttachmentRepository;
import uz.pdp.food_recipe_app.repository.FollowRepository;
import uz.pdp.food_recipe_app.repository.UserRepository;
import uz.pdp.food_recipe_app.util.GlobalVar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final AttachmentRepository attachmentRepository;

    public ResBaseMsg following(UUID id) {
        User user = GlobalVar.getUser();
        User followingUser = userRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.FOLLOWING_NOT_FOUND));
        if (user.getId().equals(followingUser.getId())) {
            throw RestException.restThrow(ErrorTypeEnum.SELF_FOLLOWING);
        }
        if (followRepository.existsByUserAndFollowing(user, followingUser)) {
            throw RestException.restThrow(ErrorTypeEnum.DUPLICATE_FOLLOWING);
        }
        Following following = new Following(user, followingUser);
        followRepository.save(following);
        return new ResBaseMsg("successfully added following user");
    }

    public List<UserRes> getAllFollowings() {
        User user = GlobalVar.getUser();
        List<Following> allByUserId = followRepository.findAllByUserId(user.getId());

        List<UserRes> userResList = new ArrayList<>();

        for (Following following : allByUserId) {
            userResList.add(UserMapper.entityToRes(following.getFollowing(),getPhotoPath(user)));
        }
        return userResList;
    }

    public List<UserRes> getAllFollowers() {
        User user = GlobalVar.getUser();
        List<Following> allByUserId = followRepository.findAllByFollowingId(user.getId());

        List<UserRes> userResList = new ArrayList<>();

        for (Following following : allByUserId) {
            userResList.add(UserMapper.entityToRes(following.getUser(),getPhotoPath(user)));
        }
        return userResList;
    }

    @Transactional
    public ResBaseMsg deleteFollowing(UUID id) {
        User user = GlobalVar.getUser();
        Following following = followRepository.findByUserIdAndFollowingId(user.getId(), id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.FOLLOWING_NOT_FOUND));
        followRepository.deleteById(following.getId());
        return new ResBaseMsg("successfully deleted following user");
    }
    @Transactional
    public ResBaseMsg deleteFollower(UUID id) {
        User user = GlobalVar.getUser();
        Following following = followRepository.findByUserIdAndFollowingId(id,user.getId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.FOLLOWER_NOT_FOUND));
        followRepository.deleteById(following.getId());
        return new ResBaseMsg("Successfully deleted following user");
    }

    private String getPhotoPath(User user) {
        if (user.getPhotoId() == null)
            return null;

        return attachmentRepository
                .findById(user.getPhotoId())
                .map(Attachment::getFilePath)
                .orElse(null);
    }
}
