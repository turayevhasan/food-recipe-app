package uz.pdp.food_recipe_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.food_recipe_app.entity.Following;

import java.util.List;
import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<Following, Long> {
    List<Following> findAllByFollowingId(UUID following_id);
    List<Following> findAllByUserId(UUID User_id);
    void deleteAllByFollowingId(UUID following_id);
    void deleteAllByUserId(UUID User_id);
    Boolean existsByFollowingId(UUID following_id);
    Boolean existsByUserId(UUID User_id);
}
