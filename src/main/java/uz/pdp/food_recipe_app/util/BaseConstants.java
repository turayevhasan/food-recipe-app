package uz.pdp.food_recipe_app.util;

public interface BaseConstants {
    String FILE_UPLOAD_PATH = "E:/PROJECT_DOWNLOAD_FILES/";

    String JWT_SECRET_KEY = "Rk9PRF9SRUNJUEVfQVBQX1BST0pFQ1RfSldUX1RPS0VOX1NFQ1JFVF9LRVk=";
    // key = FOOD_RECIPE_APP_PROJECT_JWT_TOKEN_SECRET_KEY // algorithm = base64

    long ACCESS_TOKEN_EXPIRE = 86400000;  // 1 day
    long REFRESH_TOKEN_EXPIRE = 86400000 * 7;  // 7 days

    String AUTHENTICATION_HEADER = "Authorization";
    String BEARER_TOKEN = "Bearer ";
}