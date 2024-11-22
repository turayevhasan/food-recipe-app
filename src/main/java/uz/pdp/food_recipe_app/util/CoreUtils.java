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

    public static String generateStrongPassword(int length) {
        final String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        final String digits = "0123456789";
        final String specialCharacters = "!@#$%&*";
        final String allCharacters = upperCaseLetters + lowerCaseLetters + digits + specialCharacters;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        password.append(upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length())));
        password.append(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));

        for (int i = 4; i < length; i++) {
            password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
        }

        char[] passwordArray = password.toString().toCharArray();
        for (int i = passwordArray.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[j];
            passwordArray[j] = temp;
        }

        return new String(passwordArray);
    }

}
