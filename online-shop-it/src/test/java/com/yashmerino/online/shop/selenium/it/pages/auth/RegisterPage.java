package com.yashmerino.online.shop.selenium.it.pages.auth;

import com.yashmerino.online.shop.selenium.it.pages.BasePage;
import com.yashmerino.online.shop.selenium.it.utils.Role;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 * Модель сторінки, яка представляє сторінку реєстрації за моделлю сторінок (Page Object Model).
 */
public class RegisterPage extends BasePage {

    /**
     * Елемент поля вибору ролі.
     */
    private final By roleSelectField = By.id("role");

    /**
     * Елемент пункту вибору ролі користувача.
     */
    private final By userRoleItem = By.id("user-role");

    /**
     * Елемент пункту вибору ролі продавця.
     */
    private final By sellerRoleItem = By.id("seller-role");

    /**
     * Елемент поля електронної пошти.
     */
    private final By emailTextField = By.id("email");

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
     * Кнопка "Вже є обліковий запис".
     */
    private final By haveAlreadyAccountButton = By.id("have-already-account");

    /**
     * Конструктор.
     *
     * @param driver веб-драйвер для виконання дій в браузері.
     * @param wait   очікування веб-драйвера, щоб очікувати до виконання певних умов.
     */
    public RegisterPage(final WebDriver driver, final WebDriverWait wait) {
        super(driver, wait);
    }

    /**
     * Реєструє нового користувача.
     *
     * @param role     роль користувача.
     * @param email    електронна пошта користувача.
     * @param username ім'я користувача.
     * @param password пароль користувача.
     */
    public void registerUser(final Role role, final String email, final String username, final String password) {
        selectRole(role);

        driver.findElement(emailTextField).sendKeys(email);
        driver.findElement(usernameTextField).sendKeys(username);
        driver.findElement(passwordTextField).sendKeys(password);

        driver.findElement(submitButton).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(successAlert));
    }

    /**
     * Переходить на сторінку входу, натиснувши кнопку "Вже є обліковий запис".
     */
    public void goToLoginPage() {
        driver.findElement(haveAlreadyAccountButton).click();
    }

    /**
     * Вибирає роль.
     *
     * @param role роль для вибору.
     */
    private void selectRole(final Role role) {
        driver.findElement(roleSelectField).click();

        if (role.equals(Role.USER)) {
            driver.findElement(userRoleItem).click();
        } else {
            driver.findElement(sellerRoleItem).click();
        }
    }
}
