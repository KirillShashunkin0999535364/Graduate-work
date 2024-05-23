package com.online.shop.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * Клас, що зберігає властивості.
 */
@Component
public class ApplicationProperties {
    /**
     * Секрет JWT.
     */
    @Value("${jwt.secret}")
    public String jwtSecret;

    /**
     * Прапорець використання Algolia.
     */
    @Value("${algolia.usage:false}")
    public boolean isAlgoliaUsed;

    /**
     * Приватний ключ API Algolia.
     */
    @Value("${algolia.api.key}")
    public String algoliaApiKey;

    /**
     * Ідентифікатор додатку Algolia.
     */
    @Value("${algolia.app.id}")
    public String algoliaApplicationId;

    /**
     * Назва індексу Algolia.
     */
    @Value("${algolia.index.name}")
    public String algoliaIndexName;
}
