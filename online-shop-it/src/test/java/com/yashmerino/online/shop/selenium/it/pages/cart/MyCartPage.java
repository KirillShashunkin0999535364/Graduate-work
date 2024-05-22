package com.yashmerino.online.shop.selenium.it.pages.cart;

import com.yashmerino.online.shop.selenium.it.pages.BaseHeaderPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Модель сторінки, яка представляє сторінку мого кошика за моделлю сторінок (Page Object Model).
 */
public class MyCartPage extends BaseHeaderPage {
    /**
     * Конструктор.
     *
     * @param driver веб-драйвер для виконання дій в браузері.
     * @param wait   очікування веб-драйвера, щоб очікувати до виконання певних умов.
     */
    public MyCartPage(final WebDriver driver, final WebDriverWait wait) {
        super(driver, wait);
    }

    /**
     * Перевіряє, чи існує продукт.
     *
     * @param productName назва продукту.
     * @return <code>true</code> або <code>false</code>.
     */
    public boolean productExists(final String productName) {
        try {
            driver.findElement(By.xpath(String.format("//h6[contains(string(), %s)]", productName)));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
