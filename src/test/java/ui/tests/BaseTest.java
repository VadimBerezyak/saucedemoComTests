package ui.tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.login.LoginPage;
import pages.login.Users;
import pages.products.ProductPage;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;




public class BaseTest {
    private final Users validUser = new Users("standard_user", "secret_sauce");
    private Users lockedOutUser = new Users("locked_out_user", "secret_sauce");
    private Users problemUser = new Users("problem_user", "secret_sauce");
    private Users glitchUser = new Users("performance_glitch_user", "secret_sauce");
    @BeforeEach
    public  void setup(){
        open("https://www.saucedemo.com");
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


}
