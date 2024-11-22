package uz.pdp.food_recipe_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.entity.Following;
import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.enums.ErrorTypeEnum;
import uz.pdp.food_recipe_app.exceptions.RestException;
import uz.pdp.food_recipe_app.mapper.UserMapper;
import uz.pdp.food_recipe_app.payload.auth.res.UserRes;
import uz.pdp.food_recipe_app.payload.base.ResBaseMsg;
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

    public ResBaseMsg following(UUID id) {
        User user = GlobalVar.getUser();
        User followingUser = userRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.USER_NOT_FOUND));
        Following following = new Following(user, followingUser);
        followRepository.save(following);
        return new ResBaseMsg("successfully added following user");
    }

    public List<UserRes> getAllFollowings() {
        User user = GlobalVar.getUser();
        List<Following> allByUserId = followRepository.findAllByUserId(user.getId());

        List<UserRes> userResList = new ArrayList<>();

        for (Following following : allByUserId) {
            userResList.add(UserMapper.fromEntityToDto(following.getUser()));
        }
        return userResList;
    }

    public List<UserRes> getAllFollowers() {
        User user = GlobalVar.getUser();
        List<Following> allByUserId = followRepository.findAllByFollowingId(user.getId());

        List<UserRes> userResList = new ArrayList<>();

        for (Following following : allByUserId) {
            userResList.add(UserMapper.fromEntityToDto(following.getUser()));
        }
        return userResList;
    }

    public ResBaseMsg deleteFollowing(UUID id) {
        if(followRepository.existsByUserId(id)) {
            return new ResBaseMsg("this following user does not exist");
        }
        followRepository.deleteAllByUserId(id);
        return new ResBaseMsg("successfully deleted following user");
    }

    public ResBaseMsg deleteFollower(UUID id) {
        if(followRepository.existsByUserId(id)) {
            return new ResBaseMsg("this follower user does not exist");
        }
        followRepository.deleteAllByFollowingId(id);
        return new ResBaseMsg("successfully deleted following user");
    }
}
