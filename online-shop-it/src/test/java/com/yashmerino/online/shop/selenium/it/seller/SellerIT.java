package com.yashmerino.online.shop.selenium.it.seller;

import com.yashmerino.online.shop.selenium.it.BaseIT;
import com.yashmerino.online.shop.selenium.it.pages.BasePage;
import com.yashmerino.online.shop.selenium.it.pages.auth.LoginPage;
import com.yashmerino.online.shop.selenium.it.pages.auth.RegisterPage;
import com.yashmerino.online.shop.selenium.it.pages.products.AddProductPage;
import com.yashmerino.online.shop.selenium.it.pages.products.MyProductsPage;
import com.yashmerino.online.shop.selenium.it.pages.products.ProductsPage;
import com.yashmerino.online.shop.selenium.it.utils.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тести інтеграції для продавця.
 */
class SellerIT extends BaseIT {

    /**
     * Тестує, що продукт додається і з'являється на сторінках продуктів і моїх продуктів.
     */
    @Test
    void testAddProduct() {
        final String productName = "test";

        assertTrue(createTestSellerAndLogin());

        currentPage = new ProductsPage(driver, wait);
        ((ProductsPage) currentPage).clickAddProductButton();

        currentPage = new AddProductPage(driver, wait);
        ((AddProductPage) currentPage).addProductToCard(productName);
        ((AddProductPage) currentPage).clickHomeButton();

        currentPage = new ProductsPage(driver, wait);
        assertTrue(((ProductsPage) currentPage).productExists(productName));

        ((ProductsPage) currentPage).clickMyProductsButton();
        currentPage = new MyProductsPage(driver, wait);
        assertTrue(((MyProductsPage) currentPage).productExists(productName));
    }
}
