package ui.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static io.qameta.allure.Allure.step;

public class LoginTests extends BaseTest {

    @Test
    @DisplayName("Validation of empty fields when logging")
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
    @DisplayName("Validation of delay's possibility when logging")
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
    @DisplayName("Validation of successfully login")
    @Description("Validation of successfully login")
    void successLoginTest() {
        page.loginUser("standard_user", "secret_sauce");
        step("Check that page has name PRODUCTS", () -> {
            productPage.pageTitle.shouldHave(text("PRODUCTS"));
        });

    }

    @Test
    @DisplayName("Validation of locked user login")
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
    @DisplayName("Validation of problem user login")
    @Description("Validation of problem user login")
    void problemLoginTest() {
        page.loginUser("problem_user", "secret_sauce");
        step("Check tht there is problem-user's picture on product page", () -> {
            productPage.problemPicture.shouldHave(attribute("src", "https://www.saucedemo.com/static/media/sl-404.168b1cce.jpg"));
        });
    }


}
