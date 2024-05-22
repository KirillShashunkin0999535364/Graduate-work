package com.yashmerino.online.shop.selenium.it.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 * Базовий клас для сторінок, які мають компонент заголовка.
 */
public class BaseHeaderPage extends BasePage {
    /**
     * Кнопка Мій профіль в заголовку.
     */
    protected final By profileButton = By.id("profile-button");

    /**
     * Кнопка Моя кошик в заголовку.
     */
    protected final By myCartButton = By.id("my-cart-button");

    /**
     * Кнопка Мої продукти в заголовку.
     */
    protected final By myProductsButton = By.id("my-products-button");

    /**
     * Кнопка Додати продукт в заголовку.
     */
    protected final By addProductButton = By.id("add-product-button");

    /**
     * Кнопка Додому в заголовку.
     */
    protected final By homeButton = By.id("home-title");

    /**
     * Конструктор.
     *
     * @param driver веб-драйвер для виконання дій в браузері.
     * @param wait   очікування веб-драйвера, щоб очікувати до виконання певних умов.
     */
    public BaseHeaderPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    /**
     * Клацання кнопки Додати продукт в заголовку.
     */
    public void clickAddProductButton() {
        driver.findElement(addProductButton).click();
        wait.until(ExpectedConditions.urlContains("product/add"));
    }

    /**
     * Клацання кнопки Мої продукти.
     */
    public void clickMyProductsButton() {
        driver.findElement(myProductsButton).click();
        wait.until(ExpectedConditions.urlContains("profile/products"));
    }

    /**
     * Клацання кнопки Додому.
     */
    public void clickHomeButton() {
        driver.findElement(homeButton).click();
        wait.until(ExpectedConditions.urlContains("products"));
    }

    /**
     * Клацання кнопки Мій кошик.
     */
    public void clickMyCartButton() {
        driver.findElement(myCartButton).click();
        wait.until(ExpectedConditions.urlContains("cart"));
    }
}
