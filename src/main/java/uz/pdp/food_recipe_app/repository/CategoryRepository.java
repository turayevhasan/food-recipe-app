package uz.pdp.food_recipe_app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.food_recipe_app.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    @Query("select c from Category c where (:name = '' or :name is null or lower(c.name) like lower(concat('%', :name, '%')))")
    Page<Category> findAllByFilters(
            @Param("name") String name,
            Pageable pageable
    );
}
