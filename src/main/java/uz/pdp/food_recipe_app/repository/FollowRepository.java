package uz.pdp.food_recipe_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.food_recipe_app.entity.Following;
import uz.pdp.food_recipe_app.entity.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<Following, Long> {
    List<Following> findAllByFollowingId(UUID followingId);
    List<Following> findAllByUserId(UUID userId);
    Boolean existsByFollowingIdAndUserId(UUID followingId, UUID userId);

    boolean existsByUserAndFollowing(User user, User followingUser);

    void deleteByUserIdAndFollowingId(UUID userId, UUID followingUserId);

    Optional<Following> findByUserIdAndFollowingId(UUID userId, UUID followingUserId);
}
