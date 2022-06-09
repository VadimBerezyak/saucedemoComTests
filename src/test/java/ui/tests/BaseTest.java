package ui.tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.cart.CartPage;
import pages.login.LoginPage;
import pages.products.ProductPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

@Owner("Vadim Berezyak")
@Severity(value = SeverityLevel.CRITICAL)
public class BaseTest {

    private LoginPage page;
    private ProductPage productPage;
    int index = (int) Math.abs((Math.random()*10 - 4));

    @BeforeEach

    public void setup() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open("https://www.saucedemo.com");
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        page = new LoginPage();
        productPage = new ProductPage();
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

    @Test
    @Description("Checking of cart indicator  by 'Add to cart' button click ")
    void addToCartButtonTest() {
        page.loginUser("standard_user", "secret_sauce");
        productPage.addToCartByIndex(index);
        step("Check that indicator is working", () -> {
            productPage.indicatorAddToCart.shouldHave(text("1"));
        });
        step("Check that remove-button appeared on page", () -> {
            productPage.getRemoveButtonByIndex(index).shouldBe(exist);
        });
    }

    @Test
    @Owner("Vadim Berezyak")
    @Description("Checking of 'Remove from cart' button's functionality ")
    void removeButtonTest() {
        page.loginUser("standard_user", "secret_sauce");
        productPage.addToCartByIndex(index);
        step("Click remove button on product page", () -> {
            productPage.getRemoveButtonByIndex(index).click();
        });
        step("Check that remove button disappeared", () -> {
            productPage.getRemoveButtonByIndex(index).shouldBe(not(exist));
        });

    }

    @Test
    @Owner("Vadim Berezyak")
    @Description("Checking that the product in cart is the same that was chosen by user")
    void checkProductAddedToCartTest() {
        page.loginUser("standard_user", "secret_sauce");
        productPage.addToCartByIndex(index);
        productPage.goToCartPage();
        CartPage cartPage = new CartPage();
        step("Check that cart contains product", () -> {
            cartPage.productInCart.shouldBe(exist);
        });
        step("Check that name of product in cart is same that was added by user", () -> {
            cartPage.productInCart.getText().equals(productPage.productPageTitle.getText());
        });
        step("Check that name of product in cart is visible", () -> {
            cartPage.productInCart.shouldBe(visible);
        });
        step("Check that remove-button exist on cart page", () -> {
            cartPage.removeButtonInCart.shouldBe(exist);
        });
        step("Check that user added only one product", () -> {
            cartPage.productInCartQuantity.shouldHave(text("1"));
        });
        step("Check that price of product in cart is the same", () -> {
            productPage.productPagePrice.innerText().equals(cartPage.cartPagePrice.innerText());
        });
    }

    @Test
    @Description("Checking of 'remove from cart' button's on cart page functionality ")
    void removeButtonInCartTest() {
        page.loginUser("standard_user", "secret_sauce");
        productPage.addToCartByIndex(index);
        productPage.goToCartPage();
        CartPage cartPage = new CartPage();
        cartPage.removeFromCart();
        step("Check that product was removed from cart", () -> {
            cartPage.productInCart.shouldBe(not(exist));
        });
        step("Check remove-button disappeared", () -> {
            cartPage.removeButtonInCart.shouldBe(not(exist));
        });
    }


}
