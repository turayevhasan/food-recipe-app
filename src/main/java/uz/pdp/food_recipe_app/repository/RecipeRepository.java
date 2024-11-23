package uz.pdp.food_recipe_app.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.food_recipe_app.entity.Recipe;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("""
                SELECT r FROM Recipe r
                LEFT JOIN r.category c
                WHERE (:name IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%')))
                  AND (:category IS NULL OR c.name = :category)
                  AND (:rate IS NULL OR (SELECT AVG(rt.stars) FROM Rating rt WHERE rt.recipe.id = r.id) = :rate)
                ORDER BY
                  CASE WHEN :timeType = 'NEWEST' THEN r.createdAt END DESC,
                  CASE WHEN :timeType = 'OLDEST' THEN r.createdAt END ASC,
                  CASE WHEN :timeType = 'POPULARITY' THEN
                      (SELECT COUNT(rt)
                       FROM Rating rt
                       WHERE rt.recipe = r)
                  END DESC
            """)
    List<Recipe> findAllByFilters(
            @Param("name") String name,
            @Param("category") String category,
            @Param("rate") Integer rate,
            @Param("timeType") String timeType,
            Pageable pageable);
}
