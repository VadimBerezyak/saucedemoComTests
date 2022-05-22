package ui.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.cart.CartPage;
import pages.login.LoginPage;
import pages.login.Users;
import pages.products.ProductPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;




public class BaseTest {
    private final Users emptyDataUser = new Users("", "");
    private final Users validUser = new Users("standard_user", "secret_sauce");
    private final Users lockedOutUser = new Users("locked_out_user", "secret_sauce");
    private final Users problemUser = new Users("problem_user", "secret_sauce");
    private final Users glitchUser = new Users("performance_glitch_user", "secret_sauce");
    @BeforeEach
    public  void setup(){
        open("https://www.saucedemo.com");
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }

    @Test
    void emptyFieldsLoginTest(){
        LoginPage page = new LoginPage();
        page.loginUser(emptyDataUser);
        page.errorMessageContainer.shouldHave(text("Epic sadface: Username is required"));
    }

    @Test
    void succesLoginTest(){
        LoginPage page = new LoginPage();
        page.loginUser(validUser);
        ProductPage productPage = new ProductPage();
        productPage.pageTitle.shouldHave(text("PRODUCTS"));
    }
    @Test
    void lockedLoginTest(){
        LoginPage page = new LoginPage();
        page.loginUser(lockedOutUser);
        page.errorMessageContainer.shouldHave(text("Epic sadface: Sorry, this user has been locked out."));
    }
    @Test
    void problemLoginTest(){
        LoginPage page = new LoginPage();
        page.loginUser(problemUser);
        ProductPage productPage = new ProductPage();
        productPage.problemPicture.shouldHave(attribute("src", "https://www.saucedemo.com/static/media/sl-404.168b1cce.jpg"));
    }
    @Test
    void addToCartButtonTest(){
        LoginPage page = new LoginPage();
        page.loginUser(validUser);
        ProductPage productPage = new ProductPage();
        productPage.addToCar();
        productPage.indicatorAddToCart.shouldHave(text("1"));
        productPage.removeButton.shouldBe(exist);
    }
    @Test
    void removeButtonTest(){
        LoginPage page = new LoginPage();
        page.loginUser(validUser);
        ProductPage productPage = new ProductPage();
        productPage.addToCar();
        productPage.indicatorAddToCart.shouldHave(text("1"));
        productPage.removeButton.shouldBe(exist);
        productPage.removeButton.click();
        productPage.removeButton.shouldBe(not(exist));

    }
    @Test
    void checkProductAddedToCartTest(){
        LoginPage page = new LoginPage();
        page.loginUser(validUser);
        ProductPage productPage = new ProductPage();
        productPage.addToCar();
        productPage.indicatorAddToCart.shouldHave(text("1"));
        productPage.removeButton.shouldBe(exist);
        productPage.goToCartPage();
        CartPage cartPage = new CartPage();
        cartPage.productInCart.shouldBe(exist);
        cartPage.productInCart.shouldBe(visible);
        cartPage.removeButtonInCart.shouldBe(exist);
        cartPage.productInCartQuantity.shouldHave(text("1"));
    }
    @Test
    void removeButtonInCartTest(){
        LoginPage page = new LoginPage();
        page.loginUser(validUser);
        ProductPage productPage = new ProductPage();
        productPage.addToCar();
        productPage.indicatorAddToCart.shouldHave(text("1"));
        productPage.removeButton.shouldBe(exist);
        productPage.goToCartPage();
        CartPage cartPage = new CartPage();
        cartPage.productInCart.shouldBe(exist);
        cartPage.productInCart.shouldBe(visible);
        cartPage.removeButtonInCart.shouldBe(exist);
        cartPage.productInCartQuantity.shouldHave(text("1"));
        cartPage.removeFromCart();
        cartPage.removeButtonInCart.shouldBe(not(exist));
        cartPage.productInCart.shouldBe(not(exist));

    }


}
