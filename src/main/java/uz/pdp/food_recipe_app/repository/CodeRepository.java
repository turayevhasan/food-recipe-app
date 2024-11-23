package uz.pdp.food_recipe_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.food_recipe_app.entity.Code;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {
    List<Code> findAllByEmailAndDeleted(String email, boolean deleted);
    Optional<Code> findFirstByEmailAndDeleted(String email, boolean deleted);
}
