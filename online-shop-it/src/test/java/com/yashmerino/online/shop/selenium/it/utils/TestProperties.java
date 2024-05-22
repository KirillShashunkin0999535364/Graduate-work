package com.yashmerino.online.shop.selenium.it.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
@Slf4j
public class TestProperties {

    /**
     * Файл, який зберігає властивості.
     */
    private final static String PROPERTIES_FILE = "src/test/resources/it-test.properties";

    /**
     * Властивості.
     */
    private final static Properties properties = new Properties();

    /**
     * Ім'я властивості URL бази даних.
     */
    public final static String DB_URL = "db.url";

    /**
     * Ім'я властивості користувача бази даних.
     */
    public final static String DB_USERNAME = "db.username";

    /**
     * Ім'я властивості пароля бази даних.
     */
    public final static String DB_PASSWORD = "db.password";

    static {
        try {
            properties.load(new FileInputStream(PROPERTIES_FILE));
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("Не вдалося прочитати властивості тесту: ", e);
            }
        }
    }

    /**
     * Повертає властивість.
     *
     * @param propertyName назва властивості.
     * @return Значення властивості.
     */
    public static String getProperty(final String propertyName) {
        return properties.getProperty(propertyName);
    }
}
