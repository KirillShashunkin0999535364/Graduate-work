package com.yashmerino.online.shop.selenium.it.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import static com.yashmerino.online.shop.selenium.it.utils.TestProperties.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * Утиліти для тестування.
 */
@Slf4j
public class TestUtils {
    /**
     * Файл SQL-скрипту, що очищує базу даних.
     */
    public final static String SQL_CLEAN_SCRIPT_FILE = "src/test/resources/sql/clean.sql";

    /**
     * Шаблон, що використовується для складання URL бази даних додатка.
     */
    private static final String DB_URL_PATTERN = "%s?user=%s&password=%s";

    /**
     * @return URL бази даних для підключення.
     */
    private static String getDatabaseURL() {
        return String.format(DB_URL_PATTERN, getProperty(DB_URL), getProperty(DB_USERNAME), getProperty(DB_PASSWORD));
    }

    /**
     * Виконує SQL-скрипт.
     *
     * @param scriptFile шлях до SQL-скрипту.
     */
    public static void executeSQLScript(final String scriptFile) {
        final String databaseURL = getDatabaseURL();

        final File file = new File(scriptFile);
        assertTrue(file.exists(), "Файл SQL повинен існувати: " + scriptFile);

        try (Connection connection = DriverManager.getConnection(databaseURL);) {
            ScriptUtils.executeSqlScript(connection, new FileSystemResource(file));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("SQL-скрипт не вдалося виконати: ", e);
            }
        }
    }
}
