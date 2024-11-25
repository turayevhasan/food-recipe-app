package uz.pdp.food_recipe_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.food_recipe_app.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
