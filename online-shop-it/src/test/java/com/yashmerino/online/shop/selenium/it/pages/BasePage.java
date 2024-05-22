package com.yashmerino.online.shop.selenium.it.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 * Базова сторінка для моделі сторінок (Page Object Model).
 */
public class BasePage {

    /**
     * Веб-драйвер для виконання дій в браузері.
     */
    protected final WebDriver driver;

    /**
     * Очікування веб-драйвера, щоб очікувати до виконання певних умов.
     */
    protected final WebDriverWait wait;

    /**
     * Елемент успішного повідомлення.
     */
    protected final By successAlert = By.id("alert-success");

    /**
     * Кнопка відправки.
     */
    protected final By submitButton = By.id("submit-button");

    /**
     * Конструктор.
     *
     * @param driver веб-драйвер для виконання дій в браузері.
     * @param wait   очікування веб-драйвера, щоб очікувати до виконання певних умов.
     */
    public BasePage(final WebDriver driver, final WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }
}
