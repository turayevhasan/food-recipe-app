package uz.pdp.food_recipe_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.food_recipe_app.entity.Procedure;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {
}
