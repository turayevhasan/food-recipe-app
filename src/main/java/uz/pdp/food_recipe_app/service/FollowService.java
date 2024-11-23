package uz.pdp.food_recipe_app.service;

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
        User followingUser = userRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.USER_NOT_FOUND));

        Following following = new Following(GlobalVar.getUser(), followingUser);
        followRepository.save(following);

        return new ResBaseMsg("Successfully following!");
    }

    public List<UserRes> getAllFollowings() {
        List<Following> all = followRepository.findAllByUserId(GlobalVar.getUser().getId());

        List<UserRes> res = new ArrayList<>();
        for (Following following : all) {
            res.add(
                    UserMapper.entityToRes(
                            following.getUser(),
                            getPhotoPath(following.getUser())
                    )
            );
        }
        return res;
    }


    public List<UserRes> getAllFollowers() {
        List<Following> all = followRepository.findAllByFollowingId(GlobalVar.getUser().getId());

        List<UserRes> res = new ArrayList<>();
        for (Following following : all) {
            res.add(
                    UserMapper.entityToRes(
                            following.getUser(),
                            getPhotoPath(following.getUser())
                    )
            );
        }
        return res;
    }

    public ResBaseMsg deleteFollowing(UUID id) {
        if (followRepository.existsByUserId(id)) {
            return new ResBaseMsg("This following user does not exist");
        }
        followRepository.deleteAllByUserId(id);
        return new ResBaseMsg("Successfully deleted following user");
    }

    public ResBaseMsg deleteFollower(UUID id) {
        if (followRepository.existsByUserId(id)) {
            return new ResBaseMsg("This follower user does not exist");
        }
        followRepository.deleteAllByFollowingId(id);
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
