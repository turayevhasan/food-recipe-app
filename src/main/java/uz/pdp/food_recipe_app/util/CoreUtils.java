package uz.pdp.food_recipe_app.util;

import java.security.SecureRandom;
import java.util.Objects;

public class CoreUtils {
    public static <E> E getIfExists(E newObj, E oldObj) {
        return Objects.nonNull(newObj) ? newObj : oldObj;
    }

    public static String generateSmsCode() {
        return String.valueOf((int) (Math.random() * ((999999 - 100000) + 1)) + 100000).substring(0, 4);
    }
}
