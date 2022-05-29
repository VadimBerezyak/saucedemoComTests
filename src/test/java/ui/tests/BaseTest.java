package ui.tests;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.cart.CartPage;
import pages.login.LoginPage;
import pages.products.ProductPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;


public class BaseTest {

    private LoginPage page;
    private ProductPage productPage;


    @BeforeEach

    public void setup() {
        open("https://www.saucedemo.com");
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        page = new LoginPage();
        productPage = new ProductPage();
    }

    @Test
    @Owner("Vadim Berezyak")
    void emptyFieldsLoginTest() {
        LoginPage page = new LoginPage();
        page.loginUser("", "");
        page.errorMessageContainer.shouldHave(text("Epic sadface: Username is required"));
        page.errorButtonClose.click();
        page.errorButtonClose.should(not(exist));
    }

    @Test
    @Owner("Vadim Berezyak")
    void glitchPerfomanceLoginTest() {
        LoginPage page = new LoginPage();
        page.loginUser("performance_glitch_user", "secret_sauce");
        page.errorMessageContainer.shouldNot(exist);
        productPage.pageTitle.should(exist);
    }

    @Test
    @Owner("Vadim Berezyak")
    void successLoginTest() {
        page.loginUser("standard_user", "secret_sauce");
        ProductPage productPage = new ProductPage();
        productPage.pageTitle.shouldHave(text("PRODUCTS"));

    }

    @Test
    @Owner("Vadim Berezyak")
    void lockedLoginTest() {
        page.loginUser("locked_out_user", "secret_sauce");
        page.errorMessageContainer.shouldHave(text("Epic sadface: Sorry, this user has been locked out."));
        page.errorButtonClose.click();
        page.errorButtonClose.should(not(exist));
    }

    @Test
    @Owner("Vadim Berezyak")
    void problemLoginTest() {
        page.loginUser("problem_user", "secret_sauce");
        ProductPage productPage = new ProductPage();
        productPage.problemPicture.shouldHave(attribute("src", "https://www.saucedemo.com/static/media/sl-404.168b1cce.jpg"));
    }

    @Test
    @Owner("Vadim Berezyak")
    void addToCartButtonTest() {
        page.loginUser("standard_user", "secret_sauce");
        productPage.addToCar();
        productPage.indicatorAddToCart.shouldHave(text("1"));
        productPage.removeButton.shouldBe(exist);
    }

    @Test
    @Owner("Vadim Berezyak")
    void removeButtonTest() {
        page.loginUser("standard_user", "secret_sauce");
        productPage.addToCar();
        productPage.removeButton.click();
        productPage.removeButton.shouldBe(not(exist));

    }

    @Test
    @Owner("Vadim Berezyak")
    void checkProductAddedToCartTest() {

        page.loginUser("standard_user", "secret_sauce");
        productPage.addToCar();
        productPage.goToCartPage();
        CartPage cartPage = new CartPage();
        cartPage.productInCart.shouldBe(exist);
        cartPage.productInCart.getText().equals(productPage.productPageTitle.getText());
        cartPage.productInCart.shouldBe(visible);
        cartPage.removeButtonInCart.shouldBe(exist);
        cartPage.productInCartQuantity.shouldHave(text("1"));
        productPage.productPagePrice.innerText().equals(cartPage.cartPagePrice.innerText());
    }

    @Test
    @Owner("Vadim Berezyak")
    void removeButtonInCartTest() {
        page.loginUser("standard_user", "secret_sauce");
        productPage.addToCar();
        productPage.goToCartPage();
        CartPage cartPage = new CartPage();
        String s = cartPage.removeButtonInCart.innerText();
        cartPage.removeButtonInCart.shouldHave(attribute("id"));
        cartPage.removeFromCart();
        cartPage.removeButtonInCart.shouldBe(not(exist));
        cartPage.productInCart.shouldBe(not(exist));


    }


}
