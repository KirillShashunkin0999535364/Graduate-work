package com.online.shop.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * Клас, що зберігає властивості.
 */
@Component
public class ApplicationProperties {

    @Value("${jwt.secret}")
    public String jwtSecret;

    @Value("${algolia.usage:false}")
    public boolean isAlgoliaUsed;

    @Value("${algolia.api.key}")
    public String algoliaApiKey;

    @Value("${algolia.app.id}")
    public String algoliaApplicationId;

    @Value("${algolia.index.name}")
    public String algoliaIndexName;
}
