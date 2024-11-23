package uz.pdp.food_recipe_app.payload.notification.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.food_recipe_app.entity.User;
import uz.pdp.food_recipe_app.util.FormatPatterns;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationRes {
    private Long id;
    private String title;
    private String text;
    private Boolean read;
    private Long recipeId;
    private boolean deleted;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime updatedAt;
}
