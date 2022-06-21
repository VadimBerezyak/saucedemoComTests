package ui.tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Description;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.cart.CartPage;
import pages.login.LoginPage;
import pages.products.ProductPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class LoginTests {
    private LoginPage page;
    private ProductPage productPage;
    private CartPage cartPage;

    @BeforeEach

    public void setup() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open("https://www.saucedemo.com");
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        page = new LoginPage();
        productPage = new ProductPage();
        cartPage = new CartPage();
    }

    @Test
    @Description("Validation of empty fields when logging")
    void emptyFieldsLoginTest() {
        page.loginUser("", "");
        step("Checking of validation message", () -> {
            page.errorMessageContainer.shouldHave(text("Epic sadface: Username is required"));
        });
        step("Close error message", () -> {
            page.errorButtonClose.click();
        });

        step("Check if error message was closed", () -> {
            page.errorButtonClose.should(not(exist));
        });

    }

    @Test
    @Description("Validation of delay's possibility when logging")
    void glitchPerfomanceLoginTest() {
        page.loginUser("performance_glitch_user", "secret_sauce");
        step("Check that there is no error message during authorisation", () -> {
            page.errorMessageContainer.shouldNot(exist);
        });
        step("Check that user moved to the product page", () -> {
            productPage.pageTitle.should(exist);
        });
    }

    @Test
    @Description("Validation of successfully login")
    void successLoginTest() {
        page.loginUser("standard_user", "secret_sauce");
        step("Check that page has name PRODUCTS", () -> {
            productPage.pageTitle.shouldHave(text("PRODUCTS"));
        });

    }

    @Test
    @Description("Validation of locked user login")
    void lockedLoginTest() {
        page.loginUser("locked_out_user", "secret_sauce");
        step("Check that user got error message", () -> {
            page.errorMessageContainer.shouldHave(text("Epic sadface: Sorry, this user has been locked out."));
        });
        step("Press X button to close error-message", () -> {
            page.errorButtonClose.click();
        });
        step("Check that error-message button disappeared", () -> {
            page.errorMessageContainer.shouldNot(exist);
            page.errorButtonClose.should(not(exist));
        });
    }

    @Test
    @Description("Validation of problem user login")
    void problemLoginTest() {
        page.loginUser("problem_user", "secret_sauce");
        step("Check tht there is problem-user's picture on product page", () -> {
            productPage.problemPicture.shouldHave(attribute("src", "https://www.saucedemo.com/static/media/sl-404.168b1cce.jpg"));
        });
    }


}