package com.yashmerino.online.shop.selenium.it.pages.auth;

import com.yashmerino.online.shop.selenium.it.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Модель сторінки, яка представляє сторінку входу за моделлю сторінок (Page Object Model).
 */
public class LoginPage extends BasePage {
    /**
     * Елемент поля імені користувача.
     */
    private final By usernameTextField = By.id("username");

    /**
     * Елемент поля паролю.
     */
    private final By passwordTextField = By.id("password");

    /**
     * Кнопка для відправки форми.
     */
    private final By submitButton = By.id("submit");

    /**
     * Кнопка "Не маю облікового запису".
     */
    private final By dontHaveAccountButton = By.id("dont-have-account");

    /**
     * Конструктор.
     *
     * @param driver веб-драйвер для виконання дій в браузері.
     * @param wait   очікування веб-драйвера, щоб очікувати до виконання певних умов.
     */
    public LoginPage(final WebDriver driver, final WebDriverWait wait) {
        super(driver, wait);
    }

    /**
     * Входить у систему.
     *
     * @param username ім'я користувача.
     * @param password пароль користувача.
     */
    public void loginUser(final String username, final String password) {
        driver.findElement(usernameTextField).sendKeys(username);
        driver.findElement(passwordTextField).sendKeys(password);

        driver.findElement(submitButton).click();

        wait.until(ExpectedConditions.urlContains("products"));
    }

    /**
     * Переходить на сторінку реєстрації, натиснувши кнопку "Не маю облікового запису".
     */
    public void goToRegisterPage() {
        driver.findElement(dontHaveAccountButton).click();
    }
}
