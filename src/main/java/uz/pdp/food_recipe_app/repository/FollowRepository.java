package uz.pdp.food_recipe_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.food_recipe_app.entity.Following;

import java.util.List;
import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<Following, Long> {
    List<Following> findAllByFollowingId(UUID followingId);
    List<Following> findAllByUserId(UUID userId);
    void deleteAllByFollowingId(UUID followingId);
    void deleteAllByUserId(UUID userId);
    Boolean existsByFollowingId(UUID followingId);
    Boolean existsByUserId(UUID userId);
}
