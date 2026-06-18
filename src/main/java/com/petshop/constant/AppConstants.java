package com.petshop.constant;

public class AppConstants {

    public static final String ADMIN_USERNAME = "admin@petshop.in";
    public static final String ADMIN_PASSWORD = "admin@123";
    public static final String ADMIN_EMAIL = "admin@petshop.in";

    public static final String JWT_SECRET = "petShopSecretKeyForJWTTokenGeneration123456789";
    public static final long JWT_EXPIRATION_MS = 86400000; // 24 hours

    public static final String DEFAULT_LOW_STOCK_LIMIT = "10";

    private AppConstants() {
    }
}
