package com.yashmerino.online.shop.selenium.it;

import com.yashmerino.online.shop.selenium.it.pages.BasePage;
import com.yashmerino.online.shop.selenium.it.pages.auth.LoginPage;
import com.yashmerino.online.shop.selenium.it.pages.auth.RegisterPage;
import com.yashmerino.online.shop.selenium.it.utils.Role;
import com.yashmerino.online.shop.selenium.it.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.yashmerino.online.shop.selenium.it.utils.SeleniumProperties.CHROME_DRIVER_PROPERTY;

/**
 * Базовий інтеграційний тест Selenium.
 */
public class BaseIT {

    /**
     * URL сторінки реєстрації.
     */
    protected final String REGISTER_PAGE_URL = "http://localhost:8081/#/register";

    /**
     * Таймаут у секундах.
     */
    private final Duration TIMEOUT_IN_SECONDS = Duration.ofSeconds(30);

    /**
     * Шлях до виконувача Chrome.
     */
    protected String CHROME_DRIVER_PATH = "src/test/resources/chromedriver.exe";

    /**
     * Веб-драйвер для виконання дій в браузері.
     */
    protected WebDriver driver;

    /**
     * Очікування веб-драйвера, щоб очікувати до виконання певних умов.
     */
    protected WebDriverWait wait;

    /**
     * Поточна сторінка.
     */
    protected BasePage currentPage;

    /**
     * Конструктор.
     */
    public BaseIT() {
        System.setProperty(CHROME_DRIVER_PROPERTY, CHROME_DRIVER_PATH);

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);
    }

    /**
     * Метод, який викликається перед кожним тестом.
     */
    @BeforeEach
    public void beforeEach() {
        TestUtils.executeSQLScript(TestUtils.SQL_CLEAN_SCRIPT_FILE);
    }

    /**
     * Метод, який виконується після кожного тесту.
     */
    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    /**
     * Створює тестового продавця та входить у систему.
     *
     * @return <code>true</code>, якщо все пройшло успішно, і <code>false</code> в іншому випадку.
     */
    protected boolean createTestSellerAndLogin() {
        try {
            driver.get(REGISTER_PAGE_URL);
            currentPage = new RegisterPage(driver, wait);
            ((RegisterPage) currentPage).registerUser(Role.SELLER, "test@mail.ru", "seller", "seller");

            ((RegisterPage) currentPage).goToLoginPage();

            currentPage = new LoginPage(driver, wait);
            ((LoginPage) currentPage).loginUser("seller", "seller");

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Створює тестового користувача та входить у систему.
     *
     * @return <code>true</code>, якщо все пройшло успішно, і <code>false</code> в іншому випадку.
     */
    protected boolean createTestUserAndLogin() {
        try {
            driver.get(REGISTER_PAGE_URL);
            currentPage = new RegisterPage(driver, wait);
            ((RegisterPage) currentPage).registerUser(Role.USER, "test@mail.ru", "user", "user");

            ((RegisterPage) currentPage).goToLoginPage();

            currentPage = new LoginPage(driver, wait);
            ((LoginPage) currentPage).loginUser("user", "user");

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
