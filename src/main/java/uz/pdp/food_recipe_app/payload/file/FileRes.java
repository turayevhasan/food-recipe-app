
package uz.pdp.food_recipe_app.payload.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class FileRes {
    private UUID id;
    private String filePath;
}
