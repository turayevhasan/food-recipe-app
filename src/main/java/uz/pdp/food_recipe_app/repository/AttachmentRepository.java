package uz.pdp.food_recipe_app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.food_recipe_app.entity.Attachment;

import java.util.UUID;

@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, UUID> {
}
