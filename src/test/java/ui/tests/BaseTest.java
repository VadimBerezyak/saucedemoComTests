package ui.tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import pages.cart.CartPage;
import pages.login.LoginPage;
import pages.products.ProductPage;

import static com.codeborne.selenide.Selenide.open;

public class BaseTest {

    protected LoginPage page;
    protected ProductPage productPage;
    protected CartPage cartPage;

    @BeforeEach
    protected void setup() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open("https://www.saucedemo.com");
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        page = new LoginPage();
        productPage = new ProductPage();
        cartPage = new CartPage();
    }

}
